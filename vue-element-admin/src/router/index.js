import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [{
  path: '/redirect',
  component: Layout,
  hidden: true,
  children: [{
    path: '/redirect/:path(.*)',
    component: () => import('@/views/redirect/index')
  }]
},
{
  path: '/login',
  component: () => import('@/views/login/index'),
  hidden: true
},
{
  path: '/404',
  component: () => import('@/views/error-page/404'),
  hidden: true
},
{
  path: '/401',
  component: () => import('@/views/error-page/401'),
  hidden: true
},

{
  path: '/',
  component: Layout,
  redirect: '/index',
  children: [{
    path: 'index',
    component: () => import('@/views/dashboard/index'),
    name: '首页',
    meta: {
      title: '首页',
      icon: 'dashboard',
      affix: true

    }
  }]
},
{
  path: '/dashboard',
  redirect: '/index'
},
{
  path: '/profile',
  component: Layout,
  redirect: '/profile/index',
  hidden: true,
  children: [{
    path: 'index',
    component: () => import('@/views/profile/index'),
    name: 'Profile',
    meta: {
      title: 'Profile',
      icon: 'user',
      noCache: true
    }
  }]
},

// 管理员查看用户过程
{
  path: '/clerkThrough',
  component: Layout,
  redirect: '/clerkThrough/showClerkThrough',
  name: '职工经历',
  meta: {
    title: '职工经历',
    icon: 'el-icon-user-solid'

  },
  hidden: true,
  children: [{
    path: 'showClerkThrough/:id',
    name: '',
    component: () => import('@/views/clerkThrough/showClerkThrough'),
    meta: {
      title: '职工经历',
      icon: 'tree'
    },
    hidden: true
  }]
}
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // 部门管理
  {
    path: '/department',
    component: Layout,
    redirect: '/department/listDepartment',
    name: '部门管理',
    meta: {
      title: '部门管理',
      icon: 'peoples',
      roles: ['管理员', '人事经理']
    },
    children: [{
      path: 'listDepartment',
      name: '部门列表',
      component: () => import('@/views/department/listDepartment'),
      meta: {
        title: '部门列表',
        icon: 'table',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'saveDepartment',
      name: '添加部门',
      component: () => import('@/views/department/saveDepartment'),
      meta: {
        title: '添加部门',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'updateDepartment/:id',
      name: '修改部门信息',
      component: () => import('@/views/department/saveDepartment'),
      meta: {
        title: '修改部门信息',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      },
      hidden: true
    }
    ]
  },

  // 员工管理
  {
    path: '/permissions',
    component: Layout,
    redirect: '/permissions/listClerk',
    name: '员工管理',
    meta: {
      title: '员工管理',
      icon: 'el-icon-user-solid',
      roles: ['管理员']
    },
    children: [{
      path: 'listClerk',
      name: '权限列表',
      component: () => import('@/views/permissions/listClerk'),
      meta: {
        title: '权限列表',
        icon: 'table',
        roles: ['管理员']
      }
    },
    {
      path: 'listPermissions',
      name: '查看所有管理',
      component: () => import('@/views/permissions/listPermissions'),
      meta: {
        title: '查看所有管理',
        icon: 'tree',
        roles: ['管理员']
      }
    },
    {
      path: 'lizhi',
      name: '查看离职职工',
      component: () => import('@/views/permissions/liZhi'),
      meta: {
        title: '查看离职职工',
        icon: 'tree',
        roles: ['管理员']
      }
    },
    {
      path: 'updateClerk/:id',
      name: '修改权限',
      component: () => import('@/views/permissions/saveClerk'),
      meta: {
        title: '修改权限',
        icon: 'tree',
        roles: ['管理员']
      },
      hidden: true
    }
    ]
  },
  // kaoqin管理
  {
    path: '/attendance',
    component: Layout,
    redirect: '/attendance/listAttendanceThrough',
    name: '考勤管理',
    meta: {
      title: '考勤管理',
      icon: 'el-icon-user-solid',
      roles: ['管理员', '人事经理']
    },
    children: [
      {
        path: 'listAttendanceReviewing',
        name: '正在审核列表',
        component: () => import('@/views/attendance/listAttendanceReviewing'),
        meta: {
          title: '正在审核列表',
          icon: 'table',
          roles: ['管理员', '人事经理']
        }
      }, {
        path: 'listAttendanceThrough',
        name: '审核通过列表',
        component: () => import('@/views/attendance/listAttendanceThrough'),
        meta: {
          title: '审核通过列表',
          icon: 'table',
          roles: ['管理员', '人事经理']
        }
      },
      {
        path: 'listAttendanceNotThrough',
        name: '未通过审核列表',
        component: () => import('@/views/attendance/listAttendanceNotThrough'),
        meta: {
          title: '未通过审核列表',
          icon: 'table',
          roles: ['管理员', '人事经理']
        }
      },

      {
        path: 'saveAttendance',
        name: '添加考勤',
        component: () => import('@/views/attendance/saveAttendance'),
        meta: {
          title: '添加考勤',
          icon: 'tree',
          roles: ['管理员', '人事经理']
        }
      },
      {
        path: 'updateAttendance/:id',
        name: '修改考勤',
        component: () => import('@/views/attendance/saveAttendance'),
        meta: {
          title: '修改考勤',
          icon: 'tree',
          roles: ['管理员', '人事经理']
        },
        hidden: true
      }
    ]
  },
  // 职工管理
  {
    path: '/clerk',
    component: Layout,
    redirect: '/clerk/listClerk',
    name: '职工管理',
    meta: {
      title: '职工管理',
      icon: 'el-icon-user-solid',
      roles: ['管理员', '人事经理']
    },
    children: [{
      path: 'listClerk',
      name: '职工列表',
      component: () => import('@/views/clerk/listClerk'),
      meta: {
        title: '职工列表',
        icon: 'table',
        roles: ['人事经理']
      }
    },
    {
      path: 'saveClerk',
      name: '添加职工',
      component: () => import('@/views/clerk/saveClerk'),
      meta: {
        title: '添加职工',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'updateClerk/:id',
      name: '修改职工信息',
      component: () => import('@/views/clerk/saveClerk'),
      meta: {
        title: '修改职工信息',
        icon: 'tree',
        roles: ['人事经理']
      },
      hidden: true
    }
    ]
  },
  // 奖惩管理
  {
    path: '/rewardsPunishments',
    component: Layout,
    redirect: '/rewardsPunishments/listRewardsPunishments',
    name: '奖惩管理',
    meta: {
      title: '奖惩管理',
      icon: 'el-icon-check',
      roles: ['管理员', '人事经理']
    },
    children: [{
      path: 'listRewardsPunishments',
      name: '奖惩列表',
      component: () => import('@/views/rewardsPunishments/listRewardsPunishments'),
      meta: {
        title: '奖惩列表',
        icon: 'table',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'saveRewardsPunishments',
      name: '添加奖惩',
      component: () => import('@/views/rewardsPunishments/saveRewardsPunishments'),
      meta: {
        title: '添加奖惩',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'updateRewardsPunishments/:id',
      name: '修改奖惩信息',
      component: () => import('@/views/rewardsPunishments/saveRewardsPunishments'),
      meta: {
        title: '修改奖惩信息',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      },
      hidden: true
    }
    ]
  },
  // 公告管理
  {
    path: '/announcement',
    component: Layout,
    redirect: '/announcement/listAnnouncement',
    name: '公告管理',
    meta: {
      title: '公告管理',
      icon: 'el-icon-s-order',
      roles: ['管理员', '人事经理']
    },
    children: [{
      path: 'listAnnouncement',
      name: '公告列表',
      component: () => import('@/views/announcement/listAnnouncement'),
      meta: {
        title: '公告列表',
        icon: 'table',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'saveAnnouncement',
      name: '添加公告',
      component: () => import('@/views/announcement/saveAnnouncement'),
      meta: {
        title: '添加公告',
        icon: 'tree',
        roles: ['人事经理']
      }
    },
    {
      path: 'updateAnnouncement/:id',
      name: '修改公告信息',
      component: () => import('@/views/announcement/saveAnnouncement'),
      meta: {
        title: '修改公告信息',
        icon: 'tree',
        roles: ['人事经理']
      },
      hidden: true
    }

    ]
  },
  // 合同管理
  {
    path: '/contract',
    component: Layout,
    redirect: '/contract/listContract',
    name: '合同管理',
    meta: {
      title: '合同管理',
      icon: 'el-icon-tickets',
      roles: ['管理员', '人事经理']
    },
    children: [{
      path: 'listContract',
      name: '合同列表',
      component: () => import('@/views/contract/listContract'),
      meta: {
        title: '合同列表',
        icon: 'table',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'saveContract',
      name: '添加合同',
      component: () => import('@/views/contract/saveContract'),
      meta: {
        title: '添加合同',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      }
    },
    {
      path: 'updateContract/:id',
      name: '修改合同信息',
      component: () => import('@/views/contract/saveContract'),
      meta: {
        title: '修改合同信息',
        icon: 'tree',
        roles: ['管理员', '人事经理']
      },
      hidden: true
    }

    ]
  },
  // 公告列表
  {
    path: '/inform',
    component: Layout,
    redirect: '/inform/listInform',
    name: '通知',
    meta: {
      title: '通知',
      icon: 'el-icon-message-solid',
      roles: ['管理员', '职员']
    },
    children: [{
      path: 'listInform',
      name: '通知列表',
      component: () => import('@/views/inform/listInform'),
      meta: {
        title: '通知列表',
        icon: 'el-icon-message-solid',
        roles: ['管理员', '职员']
        // affix: true,
      }
    },
    {
      path: 'showAnnouncement/:id',
      name: '通知信息',
      component: () => import('@/views/inform/showAnnouncement'),
      meta: {
        title: '通知信息',
        icon: 'tree',
        roles: ['管理员', '职员']
      },
      hidden: true
    }

    ]
  },
  // 合同信息
  {
    path: '/contractInfo',
    component: Layout,
    redirect: '/contractInfo/showContract',
    name: '合同信息',
    meta: {
      title: '合同信息',
      icon: 'el-icon-tickets',
      roles: ['职员']
    },
    children: [{
      path: 'showContract',
      name: '合同信息',
      component: () => import('@/views/contractInfo/showContract'),
      meta: {
        title: '合同信息',
        icon: 'el-icon-tickets',
        roles: ['职员']
      }
    }]
  },
  // 奖惩信息
  {
    path: '/showRewardsPunishments',
    component: Layout,
    redirect: '/showRewardsPunishments/showRewardsPunishments',
    name: '奖惩信息',
    meta: {
      title: '奖惩信息',
      icon: 'el-icon-s-release',
      roles: ['职员']
    },
    children: [{
      path: 'showRewardsPunishments',
      name: '奖惩信息',
      component: () => import('@/views/rewardsPunishmentsInfo/showRewardsPunishments'),
      meta: {
        title: '奖惩信息',
        icon: 'el-icon-s-release',
        roles: ['职员']
      }
    }]
  },
  // 考勤信息
  {
    path: '/showAttendance',
    component: Layout,
    redirect: '/showAttendance/showAttendance',
    name: '考勤信息',
    meta: {
      title: '考勤信息',
      icon: 'el-icon-s-release',
      roles: ['职员']
    },
    children: [{
      path: 'showAttendance',
      name: '考勤信息',
      component: () => import('@/views/attendanceInfo/showAttendance'),
      meta: {
        title: '考勤信息',
        icon: 'el-icon-s-release',
        roles: ['职员']
      }
    }]
  },
  // 个人信息
  {
    path: '/clerkInfo',
    component: Layout,
    redirect: '/clerkInfo/showClerk',
    name: '个人信息',
    meta: {
      title: '个人信息',
      icon: 'el-icon-s-custom',
      roles: ['职员']
    },
    children: [{
      path: 'showClerk',
      name: '个人信息',
      component: () => import('@/views/clerkInfo/showClerk'),
      meta: {
        title: '个人信息',
        icon: 'el-icon-s-custom',
        roles: ['职员']
      }
    }]
  },
  // 个性化
  {
    path: '/personalized',
    component: Layout,
    redirect: '/personalized/savePersonalized',
    name: '个性化设置',
    meta: {
      title: '个性化设置',
      icon: 'el-icon-edit',
      roles: ['职员']
    },
    children: [{
      path: 'savePersonalized',
      name: '个性化设置',
      component: () => import('@/views/personalized/savePersonalized'),
      meta: {
        title: '个性化设置',
        icon: 'el-icon-edit',
        roles: ['职员']
      }
    }]
  },
  // 修改密码
  {
    path: '/savePassword',
    component: Layout,
    redirect: '/savePassword/validationPassword',
    name: '修改密码',
    meta: {
      title: '修改密码',
      icon: 'el-icon-s-order',
      roles: ['管理员', '人事经理', '职员']

    },
    children: [{
      path: 'validationPassword',
      name: '修改密码',
      component: () => import('@/views/savePassword/validationPassword'),
      meta: {
        title: '修改密码',
        icon: 'table',
        roles: ['管理员', '人事经理', '职员']
      }
    },
    {
      path: 'savePassword/:id',
      name: '输入新密码',
      component: () => import('@/views/savePassword/savePassword'),
      meta: {
        title: '输入新密码',
        icon: 'tree',
        roles: ['管理员', '人事经理', '职员']
      },
      hidden: true
    }
    ]
  },

  // 404 page must be placed at the end !!!
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
