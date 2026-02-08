<template>
  <button class="button-delete" @click="handleDeleteBooking">×</button>
</template>

<script setup lang="ts">
import type { BookingDTO } from "../../api/bookings";
import deleteBooking from "../api";

const props = defineProps<{
  booking: BookingDTO;
}>();

const emit = defineEmits(["deleteBooking"]);

const handleDeleteBooking = async () => {
  const toDelete = confirm(
    `Вы уверены, что хотите удалить встречу ${props.booking.title}?`,
  );
  if (toDelete) {
    await deleteBooking(props.booking.id);
    emit("deleteBooking");
  }
};
</script>

<style scoped>
.button-delete {
  position: absolute;
  top: 4px;
  right: 6px;
  width: 20px;
  height: 20px;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  border-radius: 50%;
  font-size: 14px;
  cursor: pointer;
}

.button-delete:hover {
  color: rgb(255, 63, 63);
}
</style>
