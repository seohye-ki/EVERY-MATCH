import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/LoginView.vue'
import FindPwView from '@/views/FindPwView.vue'
import RegistView from '@/views/RegistView.vue'
import IdView from '@/views/regist/IdView.vue'
import NicknameView from '@/views/regist/NicknameView.vue'
import PwView from '@/views/regist/PwView.vue'
import EmailView from '@/views/regist/EmailView.vue'
import FinView from '@/views/regist/FinView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
    },
    {
      path: '/find',
      name: 'FindPw',
      component: FindPwView,
    },
    {
      path: '/regist',
      name: 'Regist',
      component: RegistView,
      children: [
        {
          path: 'id',
          name: 'Regist_id',
          component: IdView,
        },
        {
          path: 'nick',
          name: 'Regist_nick',
          component: NicknameView,
        },
        {
          path: 'pw',
          name: 'Regist_pw',
          component: PwView,
        },
        {
          path: 'email',
          name: 'Regist_email',
          component: EmailView,
        },
        {
          path: 'fin',
          name: 'Regist_fin',
          component: FinView,
        },
      ],
    },
  ],
})

export default router
