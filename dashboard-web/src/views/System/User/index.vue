<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="userFindQuery.name" clearable>
            <template #append>
              <el-button icon="el-icon-search" @click="refreshUserList"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>

      <el-table :data="userList" border stripe>
        <el-table-column prop="id" label="id"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="role" label="角色"></el-table-column>
        <el-table-column label="状态">
          <template #default="{ row }">
            <el-switch
              v-model="row.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="userStateChanged(row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="primary" icon="el-icon-edit" @click="updateUser(row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" @click="deleteUser(row.id)"></el-button>
            <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="userQuery.page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="userQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
    <el-dialog title="新增用户" v-model="addDialogVisible" width="30%" @close="addDialogClosed">
      <el-form :model="addForm" :rules="addRules" ref="addFormRef" label-width="70px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUser">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog
      title="修改用户"
      v-model="updateDialogVisible"
      width="30%"
      @close="updateDialogClosed"
    >
      <el-form :model="updateForm" :rules="updateRules" ref="updateFormRef" label-width="70px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="updateForm.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="updateForm.password"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch
            v-model="updateForm.state"
            active-color="#13ce66"
            inactive-color="#ff4949"
          ></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="updateDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUserDialog">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import { defineComponent, onMounted, reactive, ref } from 'vue'
import http from '../../../utils/request'

interface UserFindQuery {
  name: string
  page: number
  pageSize: number
}

interface UserQuery {
  name: string
  state: boolean
  roleIds: Array<number>
}

interface User {
  id: number
  name: string
  role: string
  state: boolean
}

export default defineComponent({
  setup() {
    let userList: Array<User> = reactive([])
    let total = ref()
    let addDialogVisible = ref()
    let updateDialogVisible = ref()
    let addFormRef = ref()
    let updateFormRef = ref()

    const userFindQuery = reactive<UserFindQuery>({
      name: '',
      page: 1,
      pageSize: 10
    })

    const userQuery = reactive<UserQuery>({
      name: '',
      state: true,
      roleIds: []
    })

    const addForm = reactive({
      name: '',
      password: '',
      state: true,
      roleIds: []
    })

    const updateForm = reactive({
      id: 0,
      name: '',
      password: '',
      state: true,
      roleIds: []
    })

    const addRules = reactive({
      name: [
        { required: true, message: '请输入用户名称', trigger: 'blur' },
        { min: 3, max: 5, message: '长度在 3 到 10 个字符', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入用户密码', trigger: 'blur' },
        { min: 6, max: 100, message: '长度在 6 到 15 个字符', trigger: 'blur' }
      ]
    })

    const updateRules = reactive({
      password: [
        { required: true, message: '请输入用户密码', trigger: 'blur' },
        { min: 6, max: 100, message: '长度在 6 到 15 个字符', trigger: 'blur' }
      ]
    })

    const getUserList = async () => {
      const { data: res } = await http.get('user', { params: userFindQuery })
      total.value = res.data.count
      return res.data.list
    }

    onMounted(async () => {
      refreshUserList()
    })

    const refreshUserList = async () => {
      userList.splice(0, userList.length)
      const list: Array<User> = await getUserList()
      list.forEach((p) => {
        userList.push(p)
      })
    }

    const handleSizeChange = (val: any) => {
      userFindQuery.pageSize = val
      refreshUserList()
    }

    const handleCurrentChange = (val: any) => {
      userFindQuery.page = val
      refreshUserList()
    }

    const userStateChanged = async (val: any) => {
      userQuery.state = val.state
      userQuery.name = val.name
      const { data: res } = await http.put('user/' + val.id, userQuery)
      if (res.code !== 200) {
        userQuery.state = !userQuery.state
        ElMessage.error('用户状态修改失败')
      } else {
        ElMessage.success('用户状态修改成功')
      }
    }

    const addDialogClosed = () => {
      addFormRef.value?.resetFields()
    }

    const addUser = () => {
      addFormRef.value?.validate(async (valid: boolean) => {
        if (!valid) return
        const { data: res } = await http.post('user', addForm)
        if (res.code !== 200) {
          ElMessage.error('添加用户失败')
        } else {
          refreshUserList()
          ElMessage.success('添加用户成功')
        }
        addDialogVisible.value = false
      })
    }

    const updateUser = (val: any) => {
      updateForm.id = val.id
      updateForm.name = val.name
      updateForm.state = val.state
      updateDialogVisible.value = true
    }

    const updateUserDialog = () => {
      updateFormRef.value?.validate(async (valid: boolean) => {
        if (!valid) return
        console.log(updateForm.id)
        const { data: res } = await http.put('user/' + updateForm.id, updateForm)
        if (res.code !== 200) {
          ElMessage.error('更新用户失败')
        } else {
          refreshUserList()
          ElMessage.success('更新用户成功')
        }
        updateDialogVisible.value = false
      })
    }

    const updateDialogClosed = () => {
      updateFormRef.value?.resetFields()
    }

    const deleteUser = (id: number) => {
      ElMessageBox.confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteUserById(id)
        })
        .catch(() => {
          ElMessage.info('已取消删除')
        })
    }

    const deleteUserById = async (id: number) => {
      const { data: res } = await http.delete('user/' + id)
      if (res.code !== 200) {
        ElMessage.error('删除用户失败')
      } else {
        refreshUserList()
        ElMessage.success('删除用户成功')
      }
    }

    return {
      userFindQuery,
      userQuery,
      userList,
      total,
      addForm,
      updateForm,
      addFormRef,
      updateFormRef,
      addRules,
      updateRules,
      addDialogVisible,
      updateDialogVisible,
      addDialogClosed,
      refreshUserList,
      handleSizeChange,
      handleCurrentChange,
      userStateChanged,
      addUser,
      updateUser,
      updateDialogClosed,
      updateUserDialog,
      deleteUser
    }
  }
})
</script>

<style lang="less" scoped>
@import './index.less';
</style>