<template>
    <div class="schedule-container">
        <div class="grid-header">
            <div class="header-time-col"></div>
            <div class="col-day" v-for="dayDate in daysInWeek" :key="dayDate[0]" :class="{
                'today': isToday(dayDate[1])
            }">
                <span class="day-name">{{ dayDate[0] }}</span>
                <span class="day-date">{{ dayDate[1].getDate() }}</span>
            </div>
        </div>
        <div class="grid-body">
            <div class="time-axis">
                <div class="current-time-line" v-if="getCountPxForLine !== null"
                    :style="{ top: `${getCountPxForLine}px` }">
                </div>
                <div class="time-slot" v-for="hour in HOURS_ARRAY" :key="hour">
                    {{ hour }}
                </div>
            </div>
            <div class="col-day" v-for="dayDate in daysInWeek" :key="dayDate[0]" :class="{
                'today': isToday(dayDate[1])
            }">
                <div class="booking-item" v-for="booking in getBookingsForDay(dayDate[1])" :key="booking.id" :style="{
                    top: `${getMinutesFromTime(booking.startTime)}px`,
                    height: getHeightPxOfBooking(booking),
                    backgroundColor: isMyBooking(booking) ? 'rgb(199, 252, 234)' : 'rgb(224, 242, 254)'
                }">
                    <DeleteButton v-if="isMyBooking(booking)" :booking="booking"
                        @delete-booking="getBookingsForWeek()" />
                    <div class="booking-info">
                        <strong><span>{{ booking.title }}</span></strong>
                        <span>{{ booking.userFullName }}</span>
                        <span>{{ booking.startTime }} - {{ booking.endTime }}</span>
                    </div>

                </div>
                <div class="time-cell-table" v-for="hour in HOURS_ARRAY" :key="hour">
                    <TimeSlot :start-hour="hour" :day-date="dayDate" @booking-created="getBookingsForWeek()" />
                </div>

                <div class="current-time-line" v-if="isToday(dayDate[1]) && getCountPxForLine !== null"
                    :style="{ top: `${getCountPxForLine}px` }"></div>
            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref, watch } from 'vue';
import { HOURS_ARRAY } from '../../config/constants';
import TimeSlot from '../time-slot';
import { bookingsForWeek, type BookingDTO } from '../api/bookings';
import { useUserStore } from '@/entities/user';
import DeleteButton from '../delete-button/ui';

const props = defineProps<{
    daysInWeek: Map<string, Date>
}>()

const userStore = useUserStore()

const bookingsData = ref<BookingDTO[]>([])
const currentTime = ref<Date>(new Date())

const getFirstDay = () => props.daysInWeek.values().next().value!

const formattedDate = (date: Date): string => {
    return date.toISOString().split('T')[0]!
}

const isMyBooking = (booking: BookingDTO): boolean => {
    return booking.userEmail === userStore.getUserEmail()
}

const getBookingsForDay = (day: Date): BookingDTO[] => {
    const bookings: BookingDTO[] = bookingsData.value.filter(booking => booking.date === formattedDate(day))
    return bookings
}

const getMinutesFromTime = (time: string): number => {
    const [hours, minutes] = time.split(':').map(Number)
    return (hours! - 7) * 60 + minutes!;
}

const getHeightPxOfBooking = (booking: BookingDTO): string => {
    const startTime = getMinutesFromTime(booking.startTime)
    const endTime = getMinutesFromTime(booking.endTime)
    const height = endTime - startTime
    return `${height}px`
}

const getBookingsForWeek = async (): Promise<BookingDTO[]> => {
    return bookingsData.value = await bookingsForWeek(formattedDate(getFirstDay())) || []
}

watch(getFirstDay, getBookingsForWeek, { immediate: true })

const isToday = (day: Date): boolean => {
    const today = new Date()
    return today.getDate() === day.getDate()
}

const getCountPxForLine = computed<number | null>(() => {
    const hours = currentTime.value.getHours()
    if (hours >= 7 && hours <= 21) {
        const minutesInHour = 60
        const hoursFrom7am = hours - 7;
        const minutesFrom7am: number = (hoursFrom7am * minutesInHour + currentTime.value.getMinutes())
        const countPx: number = minutesFrom7am
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
    flex: 1;
    border-radius: 0.6rem;
    border: 1px solid #e0dfdf;
    overflow: hidden;

}

.booking-item:hover {
    width: 100%;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
}

.booking-item {
    position: absolute;
    width: 95%;
    z-index: 10;
    overflow: hidden;
    border-left: 1px solid #292929;
}

.booking-info {
    width: 100%;
    height: 100%;
    padding: 6px 10px;
    padding-right: 28px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: left;
    font-size: 0.8rem;
    white-space: nowrap;
}

.grid-header {
    display: grid;
    grid-template-columns: 5rem repeat(7, 1fr);
    height: 60px;
    box-sizing: border-box;
    width: 100%;
    border-bottom: 3px solid #e0dfdf;
}

.header-time-col {
    grid-column: 1;
    border-right: 1px solid #e0dfdf;
    box-sizing: border-box;
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
    z-index: 100;
}


.col-day.today {
    background-color: rgb(242, 247, 253);
}

.grid-body {
    display: grid;
    grid-template-columns: 5rem repeat(7, 1fr);
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
    align-items: start;
    justify-content: center;
    box-sizing: border-box;
    height: 60px;
    border-bottom: 1px solid #e0dfdf;
    color: rgb(148, 148, 148);
    font-size: small;

}

.time-cell-table {
    position: relative;
    box-sizing: border-box;
    height: 60px;
    width: 100%;
    border-bottom: 1px solid #e0dfdf;
    z-index: 1;
}

.time-cell-table:hover {
    cursor: pointer;
    background-color: #f6ffff;
}

.day-name {
    color: rgb(148, 148, 148);
}

.day-date {
    font-weight: 600;
}
</style>