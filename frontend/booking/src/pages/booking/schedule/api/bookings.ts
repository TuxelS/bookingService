import { http } from "@/shared/api"

export interface BookingDTO {
  id: number
  title: string
  date: string
  startTime: string
  endTime: string
  userEmail: string
  userFullName: string
}

export const bookingsForWeek = async (
    startDate: string
): Promise<BookingDTO[] | null> => {
    const {data, status} = await http.fetchFull<BookingDTO[]>({
        url: '/bookings/week',
        method: 'GET',
        params: {
            startDate
        }
    })
    
    return status === 200 && data !== null ? data : null
}