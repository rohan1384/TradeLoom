import { Button } from '@/components/ui/button'
import { DialogClose } from '@/components/ui/dialog'
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import React from 'react'
import { useForm } from 'react-hook-form'

const PaymentDetailsForm = () => {
    const form=useForm({
        resolver:"",
        defaultValues:{
            accountHolderName:"",
            ifsc:"",
            accountNumber:"",
            bankName:"",

        }
    })

    const onSubmit=(data)=>{
         console.log(data);
    }
  return (
    <div className='px-10 py-2' >
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className='space-y-6'>
              
           <FormField
    control={form.control}
  name="accountHolderName"
  render={({ field }) => (
    <FormItem >
      <FormLabel>Account Holder Name</FormLabel>
      <FormControl>
        <Input  className="border w-full border-gray-700 p-5" placeholder="rohan" {...field} />
      </FormControl>
      <FormDescription>This is your public display name.</FormDescription>
      <FormMessage />
    </FormItem>
  )}
/>
        
<FormField
    control={form.control}
  name="ifsc"
  render={({ field }) => (
    <FormItem >
      <FormLabel>IFSC Code</FormLabel>
      <FormControl>
        <Input className="border w-full border-gray-700 p-5" placeholder="ifsc" {...field} />
      </FormControl>
      <FormDescription>This is your public display name.</FormDescription>
      <FormMessage />
    </FormItem>
  )}
  />

<FormField
    control={form.control}
  name="accountNumber"
  render={({ field }) => (
    <FormItem >
      <FormLabel>Account Number</FormLabel>
      <FormControl>
        <Input   className="border w-full border-gray-700 p-5" placeholder="*********5676" {...field} />
      </FormControl>
      <FormDescription>This is your public display name.</FormDescription>
      <FormMessage />
    </FormItem>
  )}
  />

<FormField
    control={form.control}
  name="confirmAccountNumber"
  render={({ field }) => (
    <FormItem >
      <FormLabel>Confirm Account Number</FormLabel>
      <FormControl>
        <Input  className="border w-full border-gray-700 p-5" placeholder="confirm account number" {...field} />
      </FormControl>
      <FormDescription>This is your public display name.</FormDescription>
      <FormMessage />
    </FormItem>
  )}
  />
  <FormField
    control={form.control}
  name="bankName"
  render={({ field }) => (
    <FormItem >
      <FormLabel>Bank Name</FormLabel>
      <FormControl>
        <Input  className="border w-full border-gray-700 p-5" placeholder="YES Bank" {...field} />
      </FormControl>
      <FormDescription>This is your public display name.</FormDescription>
      <FormMessage />
    </FormItem>
  )}
  />
  <DialogClose className='w-full'>
   <Button className="w-full py-5 " type="submit">Submit</Button>
   </DialogClose>
        </form>


      </Form>
    </div>
  )
}

export default PaymentDetailsForm
