<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <h3>Новая встреча</h3>
        <button class="btn" @click="$emit('close')">×</button>
      </div>

      <form class="modal-body">
        <div class="form-group">
          <label>Название</label>
          <input v-model="form.title" placeholder="Тема встречи" required />
        </div>
        <div class="row">
          <div class="form-group">
            <label>Начало</label>
            <input v-model="form.startTime" type="time" required />
          </div>
          <div class="form-group">
            <label>Конец</label>
            <input v-model="form.endTime" type="time" required />
          </div>
        </div>
        <div class="form-group">
          <label>Дата</label>
          <input
            :value="convertToString(dayDate[1])"
            type="text"
            disabled
            required
          />
        </div>
      </form>
      <div class="modal-footer">
        <button class="btn-second" @click="$emit('close')">Отмена</button>
        <button class="btn-primary" @click="handleBooking(form)">
          Сохранить
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { nextTick, reactive } from "vue";
import { HOURS_ARRAY } from "../../../config/constants";
import createBooking from "../api";

interface CreateMeetingForm {
  title: string;
  date: string;
  startTime: string;
  endTime: string;
}

const emit = defineEmits(["close", "bookingCreated"]);

const props = defineProps<{
  startHour: string;
  dayDate: [string, Date];
}>();

const form = reactive<CreateMeetingForm>({
  title: "",
  date: props.dayDate[1].toISOString().split("T")[0]!,
  startTime: "",
  endTime: "",
});

const getNextHour = (startHour: string): string => {
  if (startHour === "21:00") return "22:00";
  const index = HOURS_ARRAY.indexOf(startHour);
  return HOURS_ARRAY[index + 1]!;
};

nextTick(() => {
  form.startTime = props.startHour;
  form.endTime = getNextHour(props.startHour);
});

const convertToString = (date: Date): string => {
  return date.toLocaleString("ru-RU", {
    weekday: "long",
    day: "numeric",
    month: "long",
  });
};

const handleBooking = async (form: CreateMeetingForm) => {
  const success: boolean = await createBooking(form);
  if (success) {
    alert("Встреча успешно создана");
    emit("bookingCreated");
  } else {
    alert("Ошибка при создании встречи");
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

label {
  font-size: large;
  color: rgb(82, 81, 81);
}

input {
  border-radius: 0.3rem;
  height: 2rem;
  padding: 8px;
  border: 1px solid rgb(170, 169, 169);
  color: black;
}

input:disabled {
  background-color: #ececec;
  color: rgb(170, 169, 169);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  align-items: center;
  padding: 20px;
  height: 2rem;
  border-bottom: 1px solid rgb(214, 214, 214);
}

.modal-footer {
  display: flex;
  justify-content: right;
  align-items: center;
  padding: 20px;
  height: 2rem;
  gap: 1rem;
  border-top: 1px solid rgb(214, 214, 214);
  background-color: #f3f3f3;
}

.modal-body {
  padding: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 10px;
}

.btn-primary {
  background-color: #5c5cf5;
  font-size: 1.2rem;
  padding-right: 10px;
  padding-left: 10px;
  cursor: pointer;
  border-radius: 0.3rem;
  color: white;
  border-color: rgb(247, 247, 247);
  border: none;
}

.btn-second {
  background-color: white;
  font-size: 1.2rem;
  padding-right: 10px;
  padding-left: 10px;
  cursor: pointer;
  border-radius: 0.3rem;
  border-color: rgb(102, 102, 102);
}

.row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1em;
}

.modal-content {
  z-index: 1000;
  background: white;
  border-radius: 8px;
  position: relative;
  max-width: 400px;
  width: 100%;
  overflow: hidden;
}

.btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}
</style>
