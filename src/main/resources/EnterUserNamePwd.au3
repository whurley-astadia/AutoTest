AutoItSetOption("WinTitleMatchMode","2")
WinWait("Authentication Required")
$title = WinGetTitle("Authentication Required") ; retrives whole window title
$UN=WinGetText($title,"User Name:")
ControlSend($title,"",$UN,"t837676");Sets Username
$PWD=WinGetText($title,"Password:")
Send("{TAB 1}")
ControlSend($title,"",$PWD,"Graptree900");Sets PWD
Send("{ENTER}")