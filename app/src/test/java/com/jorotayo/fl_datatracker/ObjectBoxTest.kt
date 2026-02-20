package com.jorotayo.fl_datatracker

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.domain.model.MyObjectBox
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import io.objectbox.BoxStore
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ObjectBoxTest {
    private lateinit var boxStore: BoxStore

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        boxStore = MyObjectBox.builder().androidContext(context).build()
    }

    @After
    fun tearDown() {
        boxStore.close()
        boxStore.deleteAllFiles()
    }

    @Test
    fun testAddAndRetrieve() {
        val dataFieldBox = boxStore.boxFor(DataField::class.java)

        val test = DataField(
            dataFieldId = 0,
            presetId = 8772L,
            fieldName = "Test",
            dataFieldType = DataFieldType.SHORT_TEXT,
            first = "Liane",
            second = "Angelo",
            third = "Lynnea",
            fieldHint = null,
            isEnabled = false
        )
        dataFieldBox.put(test)

        val retrieved = dataFieldBox.all
        assert(retrieved.size == 1)
        assert(retrieved[0].fieldName == "Test")
    }
}
