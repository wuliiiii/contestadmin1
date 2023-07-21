import {defineStore} from "pinia/dist/pinia";
import {ref} from "vue";

export const useWindowStore = defineStore('windowStore',()=>{
    const windowWidth = ref(0)
    let setWindowWidth = (newWidth) => {
        windowWidth.value = newWidth
    }
    return {windowWidth, setWindowWidth}
})
