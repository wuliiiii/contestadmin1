<script setup>
import {useWindowStore} from "./store/window";
import {storeToRefs} from "pinia";
let store = useWindowStore()
window.onresize = (env,ev) => {
  console.log(store.windowWidth);
  store.$patch({windowWidth : window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth})
  console.log(store.windowWidth);
}
</script>

<template>
  <router-view></router-view>
</template>

<style>

</style>
