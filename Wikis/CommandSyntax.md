**ADD TASK**

add `<desc>` -d `<deadline: format: dd/mm/yyyy>` -s `<start time>` -e `<end time>` -v `<venue>` -c `<category>`

_alternatives:_ add, new, create, insert

**ADD INFORMATION TO TASK**

info `<taskID>` `<info>`

_alternatives:_ addinfo, adddesc, createdesc, createinfo

**ADD REMINDERS TO TASK**

rem `<taskID>` `<date>` `<time (optional)>`

_alternatives:_ reminder, remind

**ADD ALARM**

alarm `<desc (option)>` `<time number>` `<time unit>`

_alternatives:_ addalarm, setalarm, timer, settimer

**STOP ALARM**
stop

_alternatives:_ stopp, sto

**CLEAR DATA**

clc

_alternatives:_ clear, clean, clc, clar

**CLEAR SCREEN**

clcsr

_alternatives:_ clearscr, clcsr, clearsc, clearscreen

**DELETE A TASK**

del `<taskID>`

_alternatives:_ delete, del, remove, rem

**EDIT DESCRIPTION FOR TASK**

edit `<taskID>` `<new desc>`

_alternatives:_ edit, change

**SEARCH A KEYWORD(S) FOR TASK DESCRIPTION**

search `<keyword>`

_alternatives:_ search, find,

Note: In the event of multiple keyword search, only tasks containing all the keywords will be displayed.

You can also search by deadline. Or words like 'tomorrow'

**LIST TASKS**

ls `<parameter (done, undone, all) [default=all]>`

_alternatives:_ list, ls, show, display, lst

Note: You can also list by deadline. Just key in the deadline as the parameter

**REDO LAST ACTION**
redo

_alternatives:_ redo, r, re, rdo

**MARK TASK AS DONE**

done `<taskID>`

_alternatives_: done, finished, finish, completed, complete

**UNDO LAST ACTION**

undo

_alternatives:_ undo, u

Multiple undo supported now

**SHOW REMINDERS**
showrem

Note: This shows today's reminders for tasks that deadlines are keyed in

**EXIT PROGRAM**

exit

_alternatives:_ exit, quit, close, end