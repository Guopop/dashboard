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
          <el-input placeholder="请输入内容">
            <template #append>
              <el-button icon="el-icon-search"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary">添加用户</el-button>
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
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <el-button type="primary" icon="el-icon-edit"></el-button>
          <el-button type="danger" icon="el-icon-delete"></el-button>
          <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
            <el-button type="warning" icon="el-icon-setting"></el-button>
          </el-tooltip>
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
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, reactive, ref } from 'vue'
import http from '../../../utils/request'

interface UserQuery {
  name: string
  page: number
  pageSize: number
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
    let total  = ref()

    const userQuery = reactive<UserQuery>({
      name: '',
      page: 1,
      pageSize: 10
    })

    const getUserList = async () => {
      const { data: res } = await http.get('user', { params: userQuery })
      total.value = res.data.count
      console.log(total)
      return res.data.list
    }


    onMounted(async () => {
      const list: Array<User> = await getUserList()
      list.forEach((p) => {
        userList.push(p)
      })
    })

    const handleSizeChange = (val: any) => {
      console.log(`每页 ${val} 条`)
    }

    const handleCurrentChange = (val: any) => {
      console.log(`当前页: ${val}`)
    }

    return {
      userQuery,
      userList,
      total,
      handleSizeChange,
      handleCurrentChange
    }
  }
})
</script>

<style scoped>
</style>