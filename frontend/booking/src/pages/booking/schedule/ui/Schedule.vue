<template>
    <div class="schedule-container">
        <div class="grid-header">
            <div class="header-time-col"></div>
            <div class="col-day" v-for="dayDate in daysInWeek" :key="dayDate[0]">
                <span class="day-name">{{ dayDate[0]}}</span>
                <span class="day-date">{{ dayDate[1].getDate()}}</span>
            </div>
        </div>
        <div class="grid-body">
            <div class="time-axis">
                <div class="time-slot" v-for="hour in HOURS_ARRAY" :key="hour">
                    {{ hour }}
                </div>
            </div>
            <div class="col-day" v-for="dayDate in daysInWeek" :key="dayDate[0]">
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
import { HOURS_ARRAY } from '../../config/constants';
import TimeSlot from '../../ui/TimeSlot.vue';
import { bookingsForWeek, type BookingDTO } from '../api/bookings';

const props = defineProps<{
    daysInWeek: Map<string, Date>
}>()

const getBookingForWeek = async (startDate: Date): Promise<BookingDTO[] | null> => {
    const start: string = startDate.toISOString().split('T')[0]!
    const bookings = await bookingsForWeek(start)
    console.log(bookings)
    return bookings
}


getBookingForWeek(props.daysInWeek.values().next().value!)


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
}

.grid-body {
    display: grid;
    grid-template-columns: 5rem repeat(7,1fr);
    overflow: auto;
    scrollbar-width: none;
}

.time-axis {
    grid-column: 1;
    height: 100%;
    border-right: 1px solid #e0dfdf;
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