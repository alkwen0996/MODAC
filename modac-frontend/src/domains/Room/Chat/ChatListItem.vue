<script setup>
import { defineProps, computed, useCssModule, ref } from "vue";
import BASE_ASSETS_URL from "@/api/BASE_ASSETS_URL";
import catSkin1 from "@/assets/user_cats_background/cat1.png"
import catSkin2 from "@/assets/user_cats_background/cat2.png"
import catSkin3 from "@/assets/user_cats_background/cat3.png"
import catSkin4 from "@/assets/user_cats_background/cat4.png"
import catSkin5 from "@/assets/user_cats_background/cat5.png"
import catSkin6 from "@/assets/user_cats_background/cat6.png"
import catSkin7 from "@/assets/user_cats_background/cat7.png"
import catSkin8 from "@/assets/user_cats_background/cat8.png"
import catSkin9 from "@/assets/user_cats_background/cat9.png"
import catSkin10 from "@/assets/user_cats_background/cat10.png"
import catSkin11 from "@/assets/user_cats_background/cat11.png"
import catSkin12 from "@/assets/user_cats_background/cat12.png"


const catSkin = computed(() => {
  if( props.loginUser.catSkin === 1){
    return catSkin1
  }
  else if(props.loginUser.catSkin === 2){
    return catSkin2
  }
  else if(props.loginUser.catSkin === 3){
    return catSkin3
  }
  else if(props.loginUser.catSkin === 4){
    return catSkin4
  }
  else if(props.loginUser.catSkin === 5){
    return catSkin5
  }
  else if(props.loginUser.catSkin === 6){
    return catSkin6
  }
  else if(props.loginUser.catSkin === 7){
    return catSkin7
  }
  else if(props.loginUser.catSkin === 8){
    return catSkin8
  }
  else if(props.loginUser.catSkin === 9){
    return catSkin9
  }
  else if(props.loginUser.catSkin === 10){
    return catSkin10
  }
  else if(props.loginUser.catSkin === 11){
    return catSkin11
  }
  else if(props.loginUser.catSkin === 12){
    return catSkin12
  }
});


const props = defineProps({
  chatLog: Object,
  loginUser: Object,
  // catskin: Number
});

const $style = useCssModule();

const chatHostReader = computed(() => {
  if (props.chatLog.user.seq === props.loginUser.seq) {
    return "myStyle";
  } else {
    return "yourChat";
  }
});

const sizeStyle = computed(() => {
  return chatHostReader.value === "myStyle"
    ? $style.mychat_box_size
    : $style.yourchat_box_size;
});

const contentStyle = computed(() => {
  return chatHostReader.value === "myStyle"
    ? $style.mychat_box_content
    : $style.yourchat_box_content;
});

const dateStyle = computed(() => {
  const h = props.chatLog.sendTime.slice(11, 13);
  const m = props.chatLog.sendTime.slice(14, 16);
  const s = props.chatLog.sendTime.slice(17);

  return h + ":" + m;
});
</script>

<template>
  <div :class="sizeStyle">
    <small :class="$style.date_time">{{ dateStyle }}</small>
    <div :class="contentStyle">{{ props.chatLog.message }}</div>
    <div>
      <small v-if="props.chatLog.user.nickname != props.loginUser.nickname">{{
        props.chatLog.user.nickname
      }}</small>
      <img
        v-if="props.chatLog.user.nickname != props.loginUser.nickname"
        :src="catSkin"
        :class="$style.chat_profile"
        alt=""
      />
    </div>
  </div>
</template>

<style lang="css" module>
@import "ChatListItem.module.css";
</style>
