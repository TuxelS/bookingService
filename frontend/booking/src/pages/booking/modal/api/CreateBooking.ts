import { http } from "@/shared/api"

export const createBooking = async (payload: {
    title: string,
    date: string,
    startTime: string,
    endTime: string,
}): Promise<boolean> => {
    const {status} = await http.fetchFull({
        url: '/bookings',
        method: 'POST',
        data: payload
    })

    return status >= 200 && status < 300
}