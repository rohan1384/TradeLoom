import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import React from 'react'

const Withdrawal = () => {
  return (
    <div className='lg:p-20 p-5'>
    <h1 className='font-bold text-3xl pb-5 '>Withdrawal</h1>
<Table className="border">
<TableHeader>
  <TableRow>
    <TableHead className="py-5">Date </TableHead>
    <TableHead>Method</TableHead>
    <TableHead>Amount</TableHead>
    <TableHead className="text-right ">Status</TableHead>

  </TableRow>
</TableHeader>
<TableBody>
  {[1,1,1,1,1,1,1,1,1,1,1,1].map((item,index)=>
  <TableRow kay={index}>
        <TableCell>
          <p>October 17, 2024 at 18:04</p>
        </TableCell>

    
    <TableCell className="">Bank</TableCell>
    <TableCell className="">$69249</TableCell>
    <TableCell className="text-right">
         
      345
    </TableCell>

  </TableRow>
  )}
</TableBody>
</Table>
  </div>
  )
}

export default Withdrawal
