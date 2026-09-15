# Installs "SilvanusDbSync" as a hidden Windows task that starts at logon.
#
# After this you never run anything: Silvius12\silvanus.db simply stays in step
# with the emulator, with no window open.
#
# To remove it:   Unregister-ScheduledTask -TaskName SilvanusDbSync -Confirm:$false
# To check it:    Get-ScheduledTask SilvanusDbSync

$name   = "SilvanusDbSync"
$script = Join-Path $PSScriptRoot "sync-db.ps1"

$action = New-ScheduledTaskAction -Execute "powershell.exe" `
    -Argument "-NoProfile -WindowStyle Hidden -ExecutionPolicy Bypass -File `"$script`""

$trigger  = New-ScheduledTaskTrigger -AtLogOn -User $env:USERNAME
$settings = New-ScheduledTaskSettingsSet -AllowStartIfOnBatteries `
    -DontStopIfGoingOnBatteries -StartWhenAvailable -ExecutionTimeLimit ([TimeSpan]::Zero)
$principal = New-ScheduledTaskPrincipal -UserId $env:USERNAME -LogonType Interactive

Unregister-ScheduledTask -TaskName $name -Confirm:$false -ErrorAction SilentlyContinue

Register-ScheduledTask -TaskName $name -Action $action -Trigger $trigger `
    -Settings $settings -Principal $principal `
    -Description "Pulls the Silvanus app database off the emulator into the project folder." | Out-Null

Start-ScheduledTask -TaskName $name
Write-Host "Installed and started '$name'. It runs hidden and restarts at every logon." -ForegroundColor Green
