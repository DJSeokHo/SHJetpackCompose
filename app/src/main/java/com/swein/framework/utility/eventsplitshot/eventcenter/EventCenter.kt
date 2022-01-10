package com.swein.androidkotlintool.framework.utility.eventsplitshot.eventcenter

import java.lang.ref.WeakReference

data class EventObserver(
    val arrow: String,
    val objectWeakReference: WeakReference<Any>,
    val runnable: EventCenter.EventRunnable
)

object EventCenter {

    interface EventRunnable {
        fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?)
    }

    private var eventMap: MutableMap<String, MutableList<EventObserver>> = mutableMapOf()

    fun addEventObserver(arrow: String, obj: Any, runnable: EventRunnable) {
        val eventObserver = EventObserver(arrow, WeakReference(obj), runnable)
        getObserverListForArrows(arrow).add(eventObserver)
    }

    fun removeAllObserver(obj: Any) {
        val deleteList: MutableList<EventObserver> = mutableListOf()

        for(arrayList in eventMap.values) {
            deleteList.clear()

            for(observer in arrayList) {
                if(observer.objectWeakReference.get() === obj) {
                    deleteList.add(observer)
                }
            }
            arrayList.removeAll(deleteList)
        }
    }

    fun removeObserverForArrows(arrow: String, obj: Any) {

        val result: MutableList<EventObserver> = getObserverListForArrows(arrow)
        val deleteList: MutableList<EventObserver> = mutableListOf()

        var any: Any?

        for(eventObserver in result) {
            any = eventObserver.objectWeakReference.get()
            if (any === obj) {
                deleteList.add(eventObserver)
            }
        }

        result.removeAll(deleteList)
    }

    fun sendEvent(arrow: String, sender: Any, data: MutableMap<String, Any>? = null) {
        val result: MutableList<EventObserver> = getObserverListForArrows(arrow)

        for(eventObserver in result) {
            eventObserver.runnable.run(arrow, sender, data)
        }
    }

    private fun getObserverListForArrows(arrow: String): MutableList<EventObserver> {

        eventMap[arrow]?.let {
            return it
        } ?: run {
            val list = mutableListOf<EventObserver>()
            eventMap[arrow] = list
            return list
        }
    }
}