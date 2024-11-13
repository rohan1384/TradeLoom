import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import React from 'react'

const Portfolio = () => {
  return (
    <div className='lg:p-20 p-5'>
      <h1 className='font-bold text-3xl pb-5 '>Portfolio</h1>
 <Table>
  <TableHeader>
    <TableRow>
      <TableHead className="w-[100px]">Asset</TableHead>
      <TableHead>Price</TableHead>
      <TableHead>Unit</TableHead>
      <TableHead>Change</TableHead>
      <TableHead>Change%</TableHead>
      <TableHead className="text-right">VOLUME</TableHead>
    </TableRow>
  </TableHeader>
  <TableBody>
    {[1,1,1,1,1,1,1,1,1,1,1,1].map((item,index)=>
    <TableRow kay={index}>
      <TableCell className="font-medium flex items-center gap-2">
        <Avatar className="-z-50">
            <AvatarImage src="https://assets.coingecko.com/coins/images/1/standard/bitcoin.png?1696501400"/>
        </Avatar>
        <span>Bitcoin</span>
      </TableCell>
      <TableCell>BTC</TableCell>
      <TableCell>9124463121</TableCell>
      <TableCell>1364881428323</TableCell>
      <TableCell>-0.20009</TableCell>
      <TableCell className="text-right">$69249</TableCell>
    </TableRow>
    )}
  </TableBody>
</Table>
    </div>
  )
}

export default Portfolio
