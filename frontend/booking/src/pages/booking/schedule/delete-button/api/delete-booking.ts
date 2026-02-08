import { http } from "@/shared/api"

export const deleteBooking = async (bookingId: number): Promise<boolean> => {
    const {status} = await http.fetchFull({
        url: `/bookings/${bookingId}`,
        method: 'DELETE'
    })

    return status >= 200 && status < 300
}