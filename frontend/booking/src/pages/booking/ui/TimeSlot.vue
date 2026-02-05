<template>
    <div 
        class="cell"
        @click="showModel = true"
    >
        <Teleport to="body">
            <CreateMeetingModal 
                v-if="showModel"  
                @close="showModel=false"
                :start-hour="startHour"
                :day-date="dayDate"/>
        </Teleport>
    </div>
</template>

<script setup lang="ts">
import { ref, type Ref } from 'vue';
import CreateMeetingModal from '../modal/ui';


const props = defineProps<{
    startHour: string,
    dayDate: [string, Date]
}>()

const showModel: Ref<boolean> = ref(false)

</script>

<style scoped>
.cell {
    display: flex;
    height: 4rem;
    width: 100%;
    border-bottom: 1px solid #e0dfdf;
}

.cell:hover {
    cursor: pointer;
    background-color:#f0efef;
}

</style>