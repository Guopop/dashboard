<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="@/assets/img/favicon.ico" alt="" />
      </div>
      <el-form ref="resetLoginFormRef" :model="loginForm" :rules="loginRule" class="login_form">
        <el-form-item prop="name">
          <el-input v-model="loginForm.username" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="el-icon-lock"
            type="password"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <div class="btns">
            <el-button type="primary" @click="login">登录</el-button>
            <el-button type="info" @click="resetLogin">重置</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { ElMessage } from 'element-plus'
import { defineComponent, getCurrentInstance, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { Md5 } from 'ts-md5'
import http from '../../utils/request'

interface LoginForm {
  username: string
  password: string
}

export default defineComponent({
  name: 'Login',
  setup() {
    const router = useRouter()
    const resetLoginFormRef = ref()
    const loginForm = reactive<LoginForm>({
      username: 'admin',
      password: '123456'
    })

    const loginRule = reactive({
      username: [
        { required: true, message: '请输入用户名称', trigger: 'blur' },
        { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入用户密码', trigger: 'blur' },
        { min: 6, max: 100, message: '长度在 6 到 100 个字符', trigger: 'blur' }
      ]
    })

    const resetLogin = () => {
      resetLoginFormRef.value?.resetFields()
    }

    const login = () => {
      resetLoginFormRef.value?.validate(async (valid: boolean) => {
        if (!valid) {
          return
        }
        const { data: res } = await http.post('/login', {
          username: loginForm.username,
          password: Md5.hashStr(loginForm.password)
        })
        if (res.code !== 200) {
          return ElMessage.error('登录失败')
        }
        window.sessionStorage.setItem('token', res.data.token)
        ElMessage.success('登录成功')
        router.push('/')
      })
    }

    return {
      loginForm,
      loginRule,
      resetLogin,
      resetLoginFormRef,
      login
    }
  }
})
</script>

<style lang="less" scoped>
@import './index.less';
</style>