<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <img src="../../assets/img/favicon.ico" alt="" />
        <span>后台管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">
          <i :class="isCollapse ? 'el-icon-s-fold' : 'el-icon-s-unfold'"></i>
        </div>
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409bff"
          unique-opened
          :collapse="isCollapse"
          :collapse-transition="false"
          router
          :default-active = "activePath"
        >
          <el-submenu :index="item.path" v-for="item in directoryList" :key="item.id">
            <template #title>
              <i :class="item.icon"></i>
              <span>{{ item.name }}</span>
            </template>
            <el-menu-item :index="subItem.path" v-for="subItem in item.menus" :key="subItem.id" @click="saveNavState(subItem.path)">
              <template #title>
                <i :class="subItem.icon"></i>
                <span>{{ subItem.name }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { defineAsyncComponent, defineComponent, onMounted, reactive, Ref, ref, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import http from '../../utils/request'

interface Permisson {
  id: number
  name: string
  identifier: string
  type: string
  order: number
  parentId: number
  icon: string,
  path: string 
}

interface Directory extends Permisson {
  menus?: Array<Menu>
}

interface Menu extends Permisson {
  buttons?: Array<Menu>
}

interface Button extends Permisson {}

export default defineComponent({
  name: 'Home',
  setup() {
    let isCollapse = ref<boolean>(false)
    const router = useRouter()
    let directoryList = ref<Directory[]>([])
    let activePath = ref<string>('')

    const logout = () => {
      window.sessionStorage.removeItem('token')
      router.push('/login')
    }

    const getMenuList = async () => {
      const { data: res } = await http.get('permission')
      return res.data
    }

    onMounted(async () => {
      const permissonList: Array<Permisson> = await getMenuList()
      permissonList
        .filter((p) => {
          return p.type === 'DIRECTORY'
        })
        .sort((p) => p.order)
        .forEach((p) => {
          directoryList.value.push(p)
        })
      directoryList.value.forEach((directory) => {
        const menuList: Array<Menu> = []
        permissonList.forEach((permisson) => {
          if (permisson.type === 'MENU' && directory.id === permisson.parentId) {
            const path: string = '../../views/' + directory.identifier + '/' + permisson.identifier + '/index.vue'
            const r = {
              name: permisson.identifier,
              path: permisson.path,
              component: () => import(/* @vite-ignore */path)
            }
            menuList.push(permisson)
            router.addRoute('Home', r)
          }
        })
        directory.menus = menuList.sort((p) => p.order)
      })
      const p = window.sessionStorage.getItem('activePath')
      activePath.value = p == null ? '' : p
    })

    const toggleCollapse = () => {
      isCollapse.value = !isCollapse.value
    }

    const saveNavState = (path: string) => {
      window.sessionStorage.setItem('activePath', path)
      activePath.value = path
    }

    return {
      logout,
      directoryList,
      isCollapse,
      toggleCollapse,
      activePath,
      saveNavState
    }
  }
})
</script>

<style lang="less" scoped>
@import './index.less';
</style>