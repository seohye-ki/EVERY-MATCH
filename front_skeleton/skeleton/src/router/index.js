import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/LoginView.vue'
import FindPwView from '@/views/FindPwView.vue'
import RegistView from '@/views/RegistView.vue'
import IdView from '@/views/regist/IdView.vue'
import NicknameView from '@/views/regist/NicknameView.vue'
import PwView from '@/views/regist/PwView.vue'
import EmailView from '@/views/regist/EmailView.vue'
import FinView from '@/views/regist/FinView.vue'
import MainView from '@/views/MainView.vue'
import MyPageView from '@/views/MyPageView.vue'
import PasswordView from '@/views/my/PasswordView.vue'
import ChangeInfoView from '@/views/my/ChangeInfoView.vue'
import ChangePasswordView from '@/views/my/ChangePasswordView.vue'
import FavoriteTeam from '@/views/FavoriteTeam.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
    },
    {
      path: '/main',
      name: 'Main',
      component: MainView,
    },
    {
      path: '/find',
      name: 'FindPw',
      component: FindPwView,
    },
    {
      path: '/favorite',
      name: 'FavoriteTeam',
      component: FavoriteTeam,
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
    {
      path: '/myPage',
      name: 'MyPage',
      component: MyPageView,
      children: [
        {
          path: 'pw',
          name: 'Password',
          component: PasswordView,
        },
        {
          path: 'info',
          name: 'Info',
          component: ChangeInfoView,
        },
        {
          path: 'changePW',
          name: 'ChangePW',
          component: ChangePasswordView,
        },
      ],
    },
  ],
})

export default router
