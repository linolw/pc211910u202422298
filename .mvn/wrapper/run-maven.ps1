# Maven Wrapper for Windows (only-script style).
# Reads the Maven distribution URL from maven-wrapper.properties, installs it
# under the current user's .m2 directory, and passes commands to mvn.cmd.
# No maven-wrapper.jar is required.
$ErrorActionPreference = 'Stop'

try {
    $wrapperDirectory = Split-Path -Parent $PSCommandPath
    $propertiesPath = Join-Path $wrapperDirectory 'maven-wrapper.properties'
    if (-not (Test-Path -LiteralPath $propertiesPath)) {
        throw "Missing Maven wrapper configuration: $propertiesPath"
    }

    $rawProperties = Get-Content -LiteralPath $propertiesPath -Raw
    $match = [regex]::Match($rawProperties, '(?m)^\s*distributionUrl\s*=\s*(\S+)\s*$')
    if (-not $match.Success) {
        throw 'No distributionUrl found in maven-wrapper.properties.'
    }
    $distributionUrl = $match.Groups[1].Value
    $archiveName = [System.IO.Path]::GetFileName(([uri]$distributionUrl).AbsolutePath)
    if ($archiveName -notmatch '^(apache-maven-[0-9.]+)-bin\.zip$') {
        throw "Unexpected Maven distribution archive: $archiveName"
    }

    $distributionFolder = $Matches[1]
    $mavenUserHome = if ($env:MAVEN_USER_HOME) {
        $env:MAVEN_USER_HOME
    } else {
        Join-Path $HOME '.m2'
    }
    $mavenHome = Join-Path $mavenUserHome ("wrapper\dists\" + $distributionFolder)
    $mavenLauncher = Join-Path $mavenHome 'bin\mvn.cmd'

    if (-not (Test-Path -LiteralPath $mavenLauncher)) {
        $stagingDirectory = Join-Path ([System.IO.Path]::GetTempPath()) ('maven-wrapper-' + [guid]::NewGuid().ToString('N'))
        try {
            New-Item -ItemType Directory -Path $stagingDirectory -Force | Out-Null
            $archivePath = Join-Path $stagingDirectory $archiveName
            Write-Host "Downloading Apache Maven from $distributionUrl"
            [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
            Invoke-WebRequest -UseBasicParsing -Uri $distributionUrl -OutFile $archivePath

            $expandedPath = Join-Path $stagingDirectory 'expanded'
            Expand-Archive -LiteralPath $archivePath -DestinationPath $expandedPath -Force
            $sourceFolder = Join-Path $expandedPath $distributionFolder
            if (-not (Test-Path -LiteralPath (Join-Path $sourceFolder 'bin\mvn.cmd'))) {
                throw 'Downloaded Maven distribution did not contain bin/mvn.cmd.'
            }
            New-Item -ItemType Directory -Path (Split-Path -Parent $mavenHome) -Force | Out-Null
            if (-not (Test-Path -LiteralPath $mavenLauncher)) {
                Move-Item -LiteralPath $sourceFolder -Destination $mavenHome
            }
        }
        finally {
            if (Test-Path -LiteralPath $stagingDirectory) {
                Remove-Item -LiteralPath $stagingDirectory -Recurse -Force -ErrorAction SilentlyContinue
            }
        }
    }

    if (-not (Test-Path -LiteralPath $mavenLauncher)) {
        throw "Maven could not be installed: $mavenLauncher"
    }

    & $mavenLauncher @args
    exit $LASTEXITCODE
}
catch {
    Write-Host ('ERROR: ' + $_.Exception.Message) -ForegroundColor Red
    Write-Host 'Check your internet connection, Maven Central access, and JDK configuration.'
    exit 1
}
