<script setup>
import OverflowDiv from "@/components/OverflowDiv.vue";
import axios from "axios";
import { useArticleStore } from "@/stores/article";
import { ref } from "vue";
import router from "../router/index";
// ======================= 변수 모음 =============================
console.log("GithubTest2 시작");
let repoEL = ref([]);
let userName = ref("");
const GITHUB_API_SERVER = "https://api.github.com";
const GITHUB_AUTH_TOKEN_SERVER = "https://github.com/login/oauth/access_token";
const CLIENT_ID = "afaeda1b95f27932a431";
const CLIENT_SECRETS = "e65b2850d9fc7e5de605817950d2b6e1454179ff";

const articleStore = useArticleStore();

const http = axios.create({
  headers: {
    "Content-Type": "application/json;charset=utf-8",
    Accept: "application/json",
  },
});

// 인가코드(code)로 액세스 토큰 요청 후 받아와 저장(access_token)
function fetchAccessToken() {
  const code = new URL(window.location.href).searchParams.get("code");

  const ACCESS_TOKEN_URL = `${GITHUB_AUTH_TOKEN_SERVER}?client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRETS}&code=${code}`;

  http
    .post(ACCESS_TOKEN_URL)
    .then((response) => {
      // *************** DB에도 저장해줘야함 **************
      console.log(response);
      articleStore.access_token = response.data.access_token;
      console.log(articleStore.access_token);
    })
    .then(() => {
      fetchUser();
      fetchRepoList(); // 액세스 토큰 저장되면 유저 레포 정보 요청
    })
    .catch((err) => console.log(err));
}

// 액세스 토큰(access_token)으로 사용자 정보 요청
function fetchUser() {
  const headers = {
    Authorization: "Bearer " + articleStore.access_token,
    Accept: "application/vnd.github+json",
  };

  http
    .get(GITHUB_API_SERVER + "/user", { headers })
    .then((response) => {
      userName.value = response.data.login;
    })
    .catch((err) => console.log(err));
}

// 액세스 토큰(access_token)으로 유저 레포 정보 요청
function fetchRepoList() {
  const headers = {
    Authorization: "Bearer " + articleStore.access_token,
    Accept: "application/vnd.github+json",
  };

  http
    .get(GITHUB_API_SERVER + "/user/repos", { headers })
    .then((response) => {
      console.log(response.data);
      response.data.forEach((element) => {
        let el = {
          id: element.id,
          name: element.name,
          description: element.description,
          pushed_at: element.pushed_at, //마지막 푸시?
          topics: element.topics,
        };

        repoEL.value.push(el);
      });
    })
    .catch((err) => console.log(err));
}

function writeCommitMSG(user, repo) {
  router.push(`/commit/${user}/${repo}`);
}

// 기존 window에서 전달한 메시지를 받아서 처리
window.addEventListener("message", function (event) {
  if (
    event.source == window.opener &&
    event.data.message == "Hello from the parent window!"
  ) {
    console.log("Received message from parent window: " + event.data.message);
    console.log("Value: " + event.data.value);
  }
});

// window.addEventListener("message", function (e) {
//   console.log(e.source);
//   console.log(e.data);
//   console.log(e.data.message);

//   if (e.source === this.window.opener && e.data.message === "activeEditor") {
//     console.log("activeEditor", e.data.value);
//   }
// });

// ========================== 여기부터 로직 ==========================
// if(!store.access_token){

// }
fetchAccessToken();
console.log("GithubTest2 끝");
</script>

<template>
  <OverflowDiv class="overflow_div">
    <div id="wrap" class="github_div_2">
      <h1 style="align-items: center" class="font-semibold user_github_id">
        {{ userName }}님의 저장소 목록
      </h1>
      <br />
      <div style="align-items: center">
        <button class="github_new_repo_button">
          <RouterLink :to="`/createRepo/${userName}`"
            >새 저장소 생성하기</RouterLink
          >
        </button>
      </div>

      <p style="align-items: center" class="text-sm">
        저장할 곳을 선택해주세요.
      </p>

      <div
        @click="writeCommitMSG(userName, el.name)"
        v-for="el of repoEL"
        :key="el.id"
        class="repoEl2"
        style="align-items: center"
      >
        <div class="flex flex-col flex-wrap justify-between">
          <h2 class="font-semibold">
            {{ el.name }}
          </h2>
          <p class="text-gray-400 text-xs">last push: {{ el.pushed_at }}</p>
          <p class="text-sm">{{ el.description }}</p>
          <div id="github_topic_box">
            <small
            class="github_topic"
            v-for="(topic, index) in el.topics"
            :key="index"
            >#{{ topic }}&nbsp;&nbsp;</small
            >
          </div>
        </div>
      </div>
    </div>
  </OverflowDiv>
</template>

<style lang="css">
#wrap {
  text-align: center;
}
.repoEl2 {
  @apply flex px-5 py-2 bg-gray-100 rounded-xl mx-5 min-w-[50%] max-w-[50%] cursor-pointer;
  text-align: left;
}
.topic {
  color: rgb(0, 89, 255);
}
.overflow_div {
  @apply h-[80vh];
}
.github_div_2 {
  @apply flex flex-col gap-3 min-h-[40vh] pt-3 pb-6;
  align-items: center;
  font-family: "Pretendard";
}
.user_github_id {
  @apply font-semibold text-yellow-700 bg-yellow-200 border border-yellow-600 px-3 py-1 rounded-lg;
}
.github_topic_box{
  display: flex;
}
.github_topic {
  @apply font-semibold text-blue-700 bg-blue-200 border border-blue-600 px-3 py-1 rounded-full;
  margin-right: 6px;
}
.github_new_repo_button {
  @apply bg-black py-2 px-5 rounded-xl text-white text-sm w-fit self-end;
}
</style>
