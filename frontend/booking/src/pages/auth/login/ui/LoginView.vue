 <template>
   <div class="login-page">
      <div class="card">
         <div class="card-header">
            <div class="icon">
               B
            </div>
            <div class="title">
               Meeting Booker
            </div>
            <div class="subtitle">
               Введите данные для входа в систему
            </div>
         </div>
         <div class="card-body"> 
            <form @submit.prevent="loginHandler">
            <label for="emailInput">Email</label>
            <input 
               type="email" 
               id="emailInput"  
               placeholder="name@example.com" 
               required
               v-model="form.email"
               >
            <label for="passwordInput">Пароль</label>
            <input 
               type="password" 
               id="passwordInput" 
               placeholder="Введите пароль" 
               required
               v-model="form.password"
               >
            <button type="submit">Войти</button>
            </form>
         </div>
      </div>
   </div>
 </template>

<script setup lang="ts">
import { ref, type Ref } from 'vue'
import {login} from '../api'
import { BOOKING_LINK } from '@/shared/config'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/entities/user'
import { http } from '@/shared/api'

interface LoginForm {
   email: string | null
   password: string | null
}

const form: Ref<LoginForm> = ref({
   email: null,
   password: null
})

const router = useRouter()
const {setUser} = useUserStore()

const loginHandler = async (): Promise<void> => {
   if(!isFormValid()) return

   const response = await login({
      email: form.value.email!,
      password: form.value.password!
   })
   
   if (response === null) {
      alert('Пользователь не найден')
      return
   }

   setUser({
      email: form.value.email!,
      token: response.token
   })
   

   router.push(BOOKING_LINK)
}

const isFormValid = (): boolean => {
   return !!form.value.email && !!form.value.password
}
</script>

<style scoped>

.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;        
  justify-content: center;     
  background: #f8f9fa;
  padding: 1rem;
}

.card {
   background: white;
   width: 100%;
   max-width: 360px;
   padding: 1rem 0 1rem 0;
   border-radius: 8px;
   box-shadow: 0 4px 20px rgba(0,0,0,0.08);
}

.card-header {
   background: white;
   padding: 1rem 1.5rem;
   text-align: center;
   margin-bottom: 3rem;
}

.icon {
  width: 56px;              
  height: 56px;
  line-height: 56px;         
  font-size: 1.8rem;         
  font-weight: bold;
  color: white;
  background-color: #5c5cf5;
  border-radius: 5px;
  margin: 0 auto 1rem auto;  
}
.title {
   font-size: 1.7rem;
   font-weight: bold;
   margin-bottom: 1rem;
}

.subtitle {
   font-size: 1rem;
   font-weight:normal;
   color: gray;
}

.card-body {
   padding: 0 1.5rem 1.5rem; 
}

input {
   width: 100%;
   padding: 0.7rem;
   margin-bottom: 1.2rem;
   margin-top: 0.5rem;
   border: 1px solid #ddd;
   border-radius: 6px;
   font-size: 1rem;
   box-sizing: border-box; 
}

label {
   font-size: 1rem;
   font-weight:lighter;
}

button {
   width: 100%;
   padding: 0.7rem;
   border-radius: 6px;
   border: 1px solid #ddd;
   font-weight: bold;
   font-size: 1rem;
   background-color: rgb(92, 92, 245);
   color: #ddd;
}

button:hover {
   background-color: rgb(51, 51, 245);
   cursor: pointer;
}

</style>