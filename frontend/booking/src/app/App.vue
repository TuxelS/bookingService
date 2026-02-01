
<template>
  <RouterView />
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { useUserStore } from '@/entities/user';
import { http } from '@/shared/api'

onMounted(() => {
  const {isUserAuth, getUserToken} = useUserStore()
  if(isUserAuth) http.setToken(getUserToken())
})

</script>

<style>
html, body {
  margin: 0;
  padding: 0;
}
</style>