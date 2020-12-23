<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
    <img style="width:100%" src="../../assets/info.jpg" />
  </div>
  
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import editorDashboard from './editor'

export default {
  name: 'Dashboard',
  components: { adminDashboard, editorDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    if (!this.roles.includes('管理员')) {
      this.currentRole = 'editorDashboard'
    }
  }
}
</script>
