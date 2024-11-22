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

router.beforeEach((to, from, next) => {
  // 인증이 필요한 페이지에서만 가드를 적용하려면
  const token = sessionStorage.getItem('Authorization'); // 또는 쿠키에서 읽어오기

  // 특정 페이지 제외 (예: '/public-page', '/login' 같은 페이지는 제외)
  const excludedRoutes = ['Login', 'FindPw'];
  if (!token) {
    if (excludedRoutes.includes(to.name) || to.name.startsWith('Regist')) {
      if (to.name === 'Regist') {
        next({ name: 'Regist_nick'})
      } else {
        next(); // 제외된 페이지는 가드하지 않고 그대로 진행
      }
    } else {
      next({ name: 'Login' }); // 토큰이 없으면 로그인 페이지로 리다이렉트  
    }
  } else {
    if (excludedRoutes.includes(to.name) || to.name.startsWith('Regist')) {
      next({ name: 'Main' })
    } else {
      next(); // 토큰이 있으면 정상적으로 페이지 접근
    }
  }
  
});


export default router
