<template>
  <div class="header-wrapper">
    <div class="header">
      <div class="header-title">
        <div class="header-icon">B</div>
        <span>Meeting Booker</span>
        <span class="room-info">Переговорная комната</span>
      </div>
      <div class="user-profile">
        <span>{{ username }}</span>
        <img
          src="https://cdn-icons-png.flaticon.com/512/1144/1144760.png"
          alt="Avatar"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from "@/entities/user";

const userStore = useUserStore();
const username = userStore.getUserEmail().split("@")[0];
</script>

<style scoped>
.header-wrapper {
  padding: 1rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  background: white;
}

.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  height: 2rem;
  font-size: 1.4rem;
}

.header-title {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.header-icon {
  display: flex;
  justify-content: center;
  width: 32px;
  height: 32px;
  line-height: 32px;
  font-size: 1.4rem;
  font-weight: bold;
  color: white;
  background-color: #5c5cf5;
  border-radius: 5px;
}

.room-info {
  padding: 0.4rem 1rem 0.4rem 1rem;
  font-size: 0.9rem;
  font-weight: bold;
  color: rgb(148, 148, 148);
  border-radius: 1rem;
  background-color: #ececee;
}

.user-profile {
  display: flex;
  gap: 1rem;
  align-items: center;
  font-size: 1rem;
}

img {
  width: 32px;
  height: 32px;
}
</style>
