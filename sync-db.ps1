# Keeps Silvius12\silvanus.db in step with the live database on the emulator.
#
# The app runs inside the emulator, which is a separate machine with its own
# disk, so it can never write into a Windows folder directly. This pulls the
# file across every couple of seconds, which is close enough to live: save a
# row in the app and it is here before you can switch windows.
#
# Normally this runs hidden as the scheduled task "SilvanusDbSync", installed
# by install-sync-task.ps1. Run it by hand only if you want to watch it work.

$adb    = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
$remote = "/sdcard/Android/data/com.example.silvius12/files/silvanus.db"
$local  = Join-Path $PSScriptRoot "silvanus.db"

$lastRows = ""
while ($true) {
    try {
        # Idle cheaply when there is no emulator, so this costs nothing while
        # it sits in the background between sessions.
        $devices = (& $adb devices 2>$null) -match "\tdevice$"
        if (-not $devices) { Start-Sleep -Seconds 15; continue }

        & $adb pull $remote $local 2>$null | Out-Null

        $rows = "$(& $adb shell "sqlite3 $remote 'SELECT COUNT(*) FROM farmer_profile;'" 2>$null)".Trim()
        if ($rows -and $rows -ne $lastRows) {
            Write-Host ("{0}  {1} row(s)" -f (Get-Date -Format 'HH:mm:ss'), $rows) -ForegroundColor Green
            $lastRows = $rows
        }
    } catch {
        # A dropped adb connection or a restarting emulator must not kill the loop.
    }
    Start-Sleep -Seconds 2
}
