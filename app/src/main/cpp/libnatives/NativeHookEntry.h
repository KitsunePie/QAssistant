//
// Created by kinit on 5/6/21.
//

#ifndef QNOTIFIED_NATIVEHOOKENTRY_H
#define QNOTIFIED_NATIVEHOOKENTRY_H

#include "lsp_native_api.h"

struct NativeHookHandle {
    int (*hookFunction)(void *func, void *replace, void **backup);
};

void handleLoadLibrary(const char *name, void *handle);

NativeHookHandle *GetOrInitNativeHookHandle();
NativeHookHandle *GetNativeHookHandle();
bool IsNativeHookHandleAvailable();

#endif //QNOTIFIED_NATIVEHOOKENTRY_H
