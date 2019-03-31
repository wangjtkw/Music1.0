package com.example.tkw.music

import java.util.concurrent.Executors

private val EXECUTOR = Executors.newSingleThreadExecutor()


fun runOnNewThread(f:()->Unit){
    EXECUTOR.submit(f)
}



