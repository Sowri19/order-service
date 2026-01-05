<#-- Simple error page with null-safe fallbacks to avoid missing error attributes -->
<#assign errObj = error!>
<#assign title = (errObj?is_hash && errObj["error"]??)?then(errObj["error"], (errObj?string("Error")))! "Error">
<#assign messageText = (message!((errObj?is_hash && errObj["message"]??)?then(errObj["message"], errObj)))?string("An unexpected error occurred")>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; color: #212529; padding: 2rem; }
        .card { max-width: 640px; margin: 0 auto; background: #fff; border: 1px solid #dee2e6; border-radius: 8px; padding: 1.5rem; box-shadow: 0 2px 6px rgba(0,0,0,0.08); }
        h1 { margin-top: 0; font-size: 1.5rem; }
        .meta { color: #6c757d; font-size: 0.9rem; margin-top: 0.5rem; }
        .path { font-family: monospace; word-break: break-all; color: #495057; }
    </style>
</head>
<body>
<div class="card">
    <h1>${title}</h1>
    <p>${messageText}</p>
    <div class="meta">
        <div>Status: ${(status)!"N/A"}</div>
        <div>Path: <span class="path">${(path)!"/"}</span></div>
        <div>Timestamp: ${(timestamp)!"N/A"}</div>
    </div>
</div>
</body>
</html>
