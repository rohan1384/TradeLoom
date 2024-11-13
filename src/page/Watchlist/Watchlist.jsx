import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { BookmarkFilledIcon } from '@radix-ui/react-icons'
import React from 'react'

const Watchlist = () => {

  const handleRemoveToWatchlist=(value)=>{
             console.log(value);
  }
  return (
    <div className='lg:p-20 p-5'>
    <h1 className='font-bold text-3xl pb-5 '>Watchlist</h1>
<Table className="border">
<TableHeader>
  <TableRow>
    <TableHead className="py-5">COIN</TableHead>
    <TableHead>SYMBOL</TableHead>
    <TableHead>VOLUME</TableHead>
    <TableHead>MARKET CAP</TableHead>
    <TableHead>24H</TableHead>
    <TableHead className="">PRICE</TableHead>
    <TableHead className="text-right text-red-500">REMOVE</TableHead>

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
    <TableCell className="">$69249</TableCell>
    <TableCell className="text-right">
          <Button  variant="ghost" onClick={()=>handleRemoveToWatchlist(item.id)} size="icon" className="h-10 w-10" >
            <BookmarkFilledIcon className='w-6 h-6'/>
          </Button>

    </TableCell>

  </TableRow>
  )}
</TableBody>
</Table>
  </div>
  )
}

export default Watchlist
