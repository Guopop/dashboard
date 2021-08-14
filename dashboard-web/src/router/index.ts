import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import { defineAsyncComponent } from '@vue/runtime-core'
import Home from '@/views/Home/index.vue'
import Welcome from '@/components/welcome.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/login',
    name: 'Login',
    component: defineAsyncComponent(() => import('@/views/Login/index.vue'))
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.name == 'Login') {
    window.sessionStorage.removeItem('token')
  }

  if (to.name !== 'Login' && window.sessionStorage.getItem('token') == null) next({ name: 'Login' })
  else next()
})

export default router
