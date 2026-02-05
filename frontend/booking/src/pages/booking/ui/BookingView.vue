
<template>
    <div style="
        height: 100vh; 
        display: flex; 
        flex-direction:column; 
        flex: 1;"
    >
        <BookingHeader/>
        <ControlBar 
            :start-date="getFirstDayInWeek" 
            :end-date="getLastDayInWeek"
            @next-week="getNextWeek"
            @prev-week="getPrevWeek"
            @today="setCurrentWeek"
        />
        <Schedule :days-in-week="daysInWeek"/>
    </div>
</template>

<script setup lang="ts">
import BookingHeader from './BookingHeader.vue';
import ControlBar from './ControlBar.vue';
import Schedule from '../schedule/ui';
import {computed, ref} from 'vue'


enum WeekDaysRu {
  Monday = "Пн",
  Tuesday = "Вт",
  Wednesday = "Ср",
  Thursday = "Чт",
  Friday = "Пт",
  Saturday = "Сб",
  Sunday = "Вс"
}

const daysInWeek = ref(new Map<string, Date>([
    [WeekDaysRu.Monday, new Date],
    [WeekDaysRu.Tuesday, new Date],
    [WeekDaysRu.Wednesday, new Date],
    [WeekDaysRu.Thursday, new Date],
    [WeekDaysRu.Friday, new Date],
    [WeekDaysRu.Saturday, new Date],
    [WeekDaysRu.Sunday, new Date],
]))

const getFirstDayInWeek = computed(() => daysInWeek.value.get(WeekDaysRu.Monday))
const getLastDayInWeek = computed(() => daysInWeek.value.get(WeekDaysRu.Sunday))

const setCurrentWeek = () => {
    const newMap = new Map<string, Date>()
    const currentDate = new Date()
    // Получаем первый день текущей недели
    currentDate.setDate(currentDate.getDate() - currentDate.getDay() + 1)
    daysInWeek.value.forEach((date, day) => {
        newMap.set(day, new Date(currentDate))
        currentDate.setDate(currentDate.getDate() + 1)
    })
    daysInWeek.value = newMap
}

setCurrentWeek()

const getNextWeek = () => {
    const newMap = new Map<string, Date>()
    daysInWeek.value.forEach((date, day) => {
        const newDate = new Date(date)
        newDate.setDate(newDate.getDate() + 7)
        newMap.set(day, newDate)
    })
    daysInWeek.value = newMap
}

const getPrevWeek = () => {
    const newMap = new Map<string, Date>()
    daysInWeek.value.forEach((date, day) => {
        const newDate = new Date(date)
        newDate.setDate(newDate.getDate() - 7)
        newMap.set(day, newDate)
    })
    daysInWeek.value = newMap
}



</script>

<style scoped>


</style>