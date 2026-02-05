<template>
    <div class="schedule-container">
        <div class="grid-header">
            <div class="header-time-col"></div>
            <div class="col-day" 
                v-for="dayDate in daysInWeek" 
                :key="dayDate[0]"
                :class="{
                    'today': isToday(dayDate[1])
                }"
            >
                <span class="day-name">{{ dayDate[0]}}</span>
                <span class="day-date">{{ dayDate[1].getDate()}}</span>
            </div>
        </div>
        <div class="grid-body">
            <div class="time-axis">
                <div class="current-time-line"
                    v-if="getCountPxForLine!==null"
                    :style="{ top: `${getCountPxForLine}px` }"
                >
                </div>
                <div class="time-slot" v-for="hour in HOURS_ARRAY" :key="hour">
                    {{ hour }}
                </div>
            </div>
            <div class="col-day" 
                v-for="dayDate in daysInWeek" 
                :key="dayDate[0]"
                :class="{
                    'today': isToday(dayDate[1])
                }"
            >
                <div 
                    class="current-time-line"
                    v-if="isToday(dayDate[1]) && getCountPxForLine!==null"
                    :style="{ top: `${getCountPxForLine}px` }"
                ></div>
                <div class="time-cell-table" v-for="hour in HOURS_ARRAY" :key="hour">
                    <TimeSlot 
                        :start-hour="hour"
                        :day-date="dayDate"
                    />
                </div>
            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref, watch } from 'vue';
import { HOURS_ARRAY } from '../../config/constants';
import TimeSlot from '../../ui/TimeSlot.vue';
import { bookingsForWeek, type BookingDTO } from '../api/bookings';

const props = defineProps<{
    daysInWeek: Map<string, Date>
}>()

const bookingsData = ref<BookingDTO[]>([])
const currentTime = ref<Date>(new Date())

const getFirstDay = () => props.daysInWeek.values().next().value!

watch(getFirstDay, async (firstDate) => {
    if (!firstDate) {
        bookingsData.value = []
        return
    }
    
    const start = firstDate.toISOString().split('T')[0]!
    bookingsData.value = await bookingsForWeek(start) || []
    console.log(bookingsData)
}, { immediate: true }
)

const isToday = (day: Date):boolean => {
    const today = new Date()
    return today.getDate() === day.getDate()
}

// 15 слотов, 1 слот - 64px(4 rem)
const getCountPxForLine = computed<number | null>(() => {
    const hours = currentTime.value.getHours()
    if (hours >= 7 && hours <= 21) {
        const minutesFrom7am: number = currentTime.value.getMinutes() - 7 * 60
        const minutesInHour = 60
        const pixelsInHourSlot = 64
        const countPx: number = minutesFrom7am * (minutesInHour/pixelsInHourSlot)
        return countPx
    }
    return null
})

let timerId: number

onMounted(() => {
    timerId = setInterval(() => {
        currentTime.value = new Date()
    }, 60000)
})

onUnmounted(() => {
    if (timerId) clearInterval(timerId)
})

</script>

<style scoped>

.schedule-container {
    display: flex;
    flex-direction: column;
    margin-left: 1rem;
    margin-right: 1rem;
    margin-bottom: 1rem;
    background: white;
    flex:1;
    border-radius: 0.6rem;
    border: 1px solid #e0dfdf;
    overflow: hidden;
    
}

.grid-header {
    display: grid;
    grid-template-columns: 5rem repeat(7, 1fr);
    height: 4rem;
    width: 100%;
    border-bottom: 3px solid #e0dfdf;
}

.header-time-col {
    grid-column: 1;
    border-right: 1px solid #e0dfdf;
}

.col-day {
    border-right: 1px solid #e0dfdf;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: relative;
}

.current-time-line {
  position: absolute;
  top: 0;
  left: 0;
  right: 0; 
  height: 2px;
  background-color: #ff3b30;
}

.col-day.today {
    background-color: rgb(217, 235, 252);
}

.grid-body {
    display: grid;
    grid-template-columns: 5rem repeat(7,1fr);
    overflow: auto;
    scrollbar-width: none;
}

.time-axis {
    grid-column: 1;
    border-right: 1px solid #e0dfdf;
    position: relative;
}

.time-slot {
    display: flex;
    align-items:start;
    justify-content: center;
    height: 4rem;
    border-bottom: 1px solid #e0dfdf;
    color: rgb(148, 148, 148);
    font-size: small;
}

.time-cell-table {
    height: 4rem;
    width: 100%;
    border-bottom: 1px solid #e0dfdf;
}

.day-name {
    color: rgb(148, 148, 148);
}
.day-date {
    font-weight:600;
}

</style>