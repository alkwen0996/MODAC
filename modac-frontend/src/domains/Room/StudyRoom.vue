<script setup>
import SideBarToggleButton from "@/components/SideBarToggleButton.vue";
import LeftSideBar from "@/domains/Room/Sidebar/LeftSideBar.vue";
import MainSection from "@/domains/Room/MainSection.vue";
import RightSideBar from "@/domains/Room/Sidebar/RightSideBar.vue";
import Wrapper from "@/components/Wrapper.vue";
import OverflowDiv from "@/components/OverflowDiv.vue";

import { useRoomStore } from "@/stores/room";
import { storeToRefs } from "pinia";
import { computed, onBeforeMount, onMounted } from "vue";

const roomStore = useRoomStore();
const { isOpenedLeftSideBar, isOpenedRightSideBar, room_info } =
  storeToRefs(roomStore);

const leftSideBarStyleState = computed(() =>
  isOpenedLeftSideBar.value
    ? "move_side_bar_to_default"
    : "move_side_bar_to_right"
);
const rightSideBarStyleState = computed(() =>
  isOpenedRightSideBar.value
    ? "move_side_bar_to_default"
    : "move_side_bar_to_left"
);
const leftSideBarArrowState = computed(() =>
  isOpenedLeftSideBar.value ? "<" : ">"
);
const rightSideBarArrowState = computed(
  () => (isOpenedRightSideBar.value ? "<" : ">") // button이 180도 회전되어있기 때문에 ">"가 왼쪽으로 가는 것이다.
);

const backGroundImg = computed(() => {
  if (room_info.value.multiTheme === "기본") {
    return "study_room_modac";
  } else if (room_info.value.multiTheme === "우주") {
    return "study_room_space";
  } else if (room_info.value.multiTheme === "바다") {
    return "study_room_beach";
  } else {
    return "study_room_dessert";
  }
});

const unLoadEvent = (event) => {
  roomStore.exitRoom();
  // event.preventDefault();
  // event.returnValue = "";
};

onMounted(() => {
  window.addEventListener("beforeunload", unLoadEvent);
  setTimeout(() => {
    roomStore.connect();
  }, 500);
});

onBeforeMount(() => {
  window.removeEventListener("beforeunload", unLoadEvent);
});
</script>

<template>
  <OverflowDiv>
    <Wrapper :class="$style[backGroundImg]">
      <Wrapper
        :class="`${$style[leftSideBarStyleState]} ${$style.left_side_bar_wrapper} ${$style.side_bar_wrapper_flex}`"
      >
        <LeftSideBar :class="$style.side_bar_content" />
        <SideBarToggleButton
          :class="$style.flex_1_12"
          @click="roomStore.toggleLeftSideBar"
          >{{ leftSideBarArrowState }}</SideBarToggleButton
        >
      </Wrapper>
      <MainSection />
      <Wrapper
        :class="`${$style[rightSideBarStyleState]} ${$style.right_side_bar_wrapper} ${$style.side_bar_wrapper_flex}`"
      >
        <SideBarToggleButton
          :class="`${$style.rotate_180} ${$style.flex_1_12}`"
          @click="roomStore.toggleRightSideBar"
          >{{ rightSideBarArrowState }}</SideBarToggleButton
        >
        <RightSideBar :class="$style.side_bar_content" />
      </Wrapper>
    </Wrapper>
  </OverflowDiv>
</template>
<style lang="css" module>
@import "./StudyRoom.module.css";
</style>
