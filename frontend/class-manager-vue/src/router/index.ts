import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '../views/LoginPage.vue'
import WelcomePage from '../views/WelcomePage.vue'
import HomePage from '@/views/HomePage.vue' // 引入 HomePage 组件

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'loginPage',
      component: LoginPage,
    },
    {
      path: '/welcome',
      name: 'Welcome',
      component: WelcomePage,
      meta: { requiresAuth: true },
    },
    {
      path: '/home',
      name: 'Home',
      component: HomePage,
      meta: { requiresAuth: true }, // 需要登录才能访问
    },
  ],
})

// 路由守卫
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && localStorage.getItem('isLogin') !== 'true') {
    next('/') // 未登录，跳转到登录页
  } else {
    next() // 已登录或不需要登录，正常访问
  }
})

export default router
