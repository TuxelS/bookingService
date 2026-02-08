<template>
  <div
    @click="showModel = true"
    :style="{
      height: '100%',
      width: '100%',
    }"
  >
    <Teleport to="body">
      <CreateMeetingModal
        v-if="showModel"
        @close="showModel = false"
        :start-hour="startHour"
        :day-date="dayDate"
        @booking-created="handleBookingCreated"
      />
    </Teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, type Ref } from "vue";
import CreateMeetingModal from "../modal/ui";

const props = defineProps<{
  startHour: string;
  dayDate: [string, Date];
}>();

const emit = defineEmits(["bookingCreated"]);

const showModel: Ref<boolean> = ref(false);

const handleBookingCreated = () => {
  showModel.value = false;
  emit("bookingCreated");
};
</script>
