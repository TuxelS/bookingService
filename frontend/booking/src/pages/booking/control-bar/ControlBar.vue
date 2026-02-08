<template>
  <div class="controls-bar">
    <div class="week-navigator">
      <button class="btn" title="Предыдущая неделя" @click="sendPrevWeek">
        ◀
      </button>
      <span class="day-month">
        {{ formatDate(startDate!) }}
        -
        {{ formatDate(endDate!) }}
      </span>
      <button class="btn" title="Следующая неделя" @click="sendNextWeek">
        ▶
      </button>
    </div>
    <button class="today" @click="today">Сегодня</button>
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits(["nextWeek", "prevWeek", "today"]);

const props = defineProps<{
  startDate: Date | undefined;
  endDate: Date | undefined;
}>();

const sendNextWeek = (): void => {
  emit("nextWeek");
};

const sendPrevWeek = (): void => {
  emit("prevWeek");
};

const today = (): void => {
  emit("today");
};

const formatDate = (date: Date): string => {
  const monthAndDay = date.toLocaleString("ru-RU", {
    month: "long",
    day: "numeric",
  });
  return `${monthAndDay}`;
};
</script>

<style scoped>
.controls-bar {
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: space-between;
  height: 4rem;
  margin-left: 1rem;
  margin-right: 1rem;
}

.week-navigator {
  gap: 0.8rem;
  display: flex;
  align-items: center;
  background: white;
  height: 2.4rem;
  border-radius: 0.2rem;
  font-size: large;
  border: 1px solid #e0dfdf;
}

.btn {
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin: 0.4rem;
  border-radius: 0.1rem;
  padding-top: 0.2rem;
  padding-bottom: 0.2rem;
}

.today {
  font-size: 0.8rem;
  background-color: white;
  border: none;
  cursor: pointer;
  height: 1.5rem;
  padding-left: 1rem;
  padding-right: 1rem;
  border-radius: 0.2rem;
  border: 1px solid #e0dfdf;
}

.today:hover {
  background-color: rgb(245, 242, 242);
  outline: 1px solid #bbbbbb;
}

.day-month {
  text-align: center;
  width: 200px;
  white-space: nowrap;
}

.btn:hover {
  background-color: rgb(245, 242, 242);
  outline: 1px solid #bbbbbb;
}
</style>
