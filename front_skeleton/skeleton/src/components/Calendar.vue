<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'

export default {
  components: {
    FullCalendar // make the <FullCalendar> tag available
  },
  data() {
    return {
      events: [
        { title: 'Event 1', date: '2024-11-20' },
        { title: 'Event 2', date: '2024-11-20' },
        { title: 'Event 3', date: '2024-11-21' },
        { title: 'Event 4', date: '2024-11-22' },
        { title: 'Event 5', date: '2024-11-22' },
        { title: 'Event 6', date: '2024-11-22' },
        { title: 'Meeting', date: '2024-11-20' }
      ],

      calendarOptions: {
        plugins: [dayGridPlugin],
        initialView: 'dayGridMonth',
        weekends: true,
        events: [],
        // 날짜 셀이 렌더링된 후, 이벤트 개수에 따라 색상을 변경
        dayCellDidMount: (info) => {
          // 날짜별 이벤트 개수 계산
          const eventCount = this.getEventCount(this.formatDate(info.date));

          // 색상의 강도를 이벤트 개수에 따라 조정
          let color = `rgba(0, 0, 0, 0)`; // 기본 투명한 색

          if (eventCount > 0) {
            const intensity = Math.min(eventCount * 0.2, 1); // 이벤트 개수에 따라 색의 진하기
            color = `rgba(255, 99, 132, ${intensity})`; // 진한 빨간색
          }

          info.el.style.backgroundColor = color;
        }
      }
    }
  },
  methods: {
    // 날짜별로 이벤트 개수를 반환하는 메소드
    getEventCount(dateStr) {
      // 해당 날짜에 해당하는 이벤트 개수 세기
      return this.events.filter(event => event.date === dateStr).length;
    },

    // FullCalendar의 날짜 객체를 'YYYY-MM-DD' 형식의 문자열로 변환
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    }
  },
}
</script>

<template>
    <FullCalendar :options="calendarOptions" />
</template>

<style scoped>
</style>
