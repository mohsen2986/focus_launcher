package dev.mslalith.focuslauncher.core.launcherapps.parser

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import dev.mslalith.focuslauncher.core.detekt.IgnoreCyclomaticComplexMethod
import dev.mslalith.focuslauncher.core.detekt.IgnoreNestedBlockDepth
import dev.mslalith.focuslauncher.core.launcherapps.model.SimpleDrawableInfo
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

internal class IconPackXmlParser(
    private val context: Context,
    private val iconPackPackageName: String
) {

    private var iconPackResources: Resources? = null

    private val iconPackToDrawablesMap: HashMap<String, HashSet<SimpleDrawableInfo>> = HashMap(0)
    private val backImages = mutableListOf<SimpleDrawableInfo>()
    private var maskImage: SimpleDrawableInfo? = null
    private var frontImage: SimpleDrawableInfo? = null
    private var scaleFactor = 1.0f

    val packageName: String
        get() = iconPackPackageName

    fun load() {
        try {
            iconPackResources = context.packageManager.getResourcesForApplication(iconPackPackageName)
        } catch (ignored: PackageManager.NameNotFoundException) {
        }

        parseAppFilterXML()
    }

    fun drawableFor(componentName: String): Drawable? {
        val set = iconPackToDrawablesMap[componentName]
        if (set.isNullOrEmpty()) return null
        return iconPackResources?.getDrawable(set.first().drawableId)
    }

    @SuppressLint("DiscouragedApi")
    @IgnoreNestedBlockDepth
    @IgnoreCyclomaticComplexMethod
    private fun parseAppFilterXML() {
        val packResources = iconPackResources ?: return
        try {
            val xmlPullParser = findAppFilterXml() ?: return
            var eventType = xmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    when {
                        xmlPullParser.name == "iconback" -> {
                            for (i in 0 until xmlPullParser.attributeCount) {
                                if (xmlPullParser.getAttributeName(i).startsWith("img")) {
                                    val drawableName = xmlPullParser.getAttributeValue(i)
                                    val drawableId = packResources.getIdentifier(drawableName, "drawable", iconPackPackageName)
                                    if (drawableId != 0) backImages.add(SimpleDrawableInfo(drawableName, drawableId))
                                }
                            }
                        }
                        xmlPullParser.name == "iconmask" -> {
                            if (xmlPullParser.attributeCount > 0 && xmlPullParser.getAttributeName(0) == "img1") {
                                val drawableName = xmlPullParser.getAttributeValue(0)
                                val drawableId = packResources.getIdentifier(drawableName, "drawable", iconPackPackageName)
                                if (drawableId != 0) maskImage = SimpleDrawableInfo(drawableName, drawableId)
                            }
                        }
                        xmlPullParser.name == "iconupon" -> {
                            if (xmlPullParser.attributeCount > 0 && xmlPullParser.getAttributeName(0) == "img1") {
                                val drawableName = xmlPullParser.getAttributeValue(0)
                                val drawableId = packResources.getIdentifier(drawableName, "drawable", iconPackPackageName)
                                if (drawableId != 0) frontImage = SimpleDrawableInfo(drawableName, drawableId)
                            }
                        }
                        xmlPullParser.name == "scale" && xmlPullParser.attributeCount > 0 && xmlPullParser.getAttributeName(0) == "factor" -> {
                            try {
                                scaleFactor = xmlPullParser.getAttributeValue(0).toFloat()
                            } catch (ignored: NumberFormatException) {
                            }
                        }
                        xmlPullParser.name == "item" -> {
                            var componentName: String? = null
                            var drawableName: String? = null
                            for (i in 0 until xmlPullParser.attributeCount) {
                                if (xmlPullParser.getAttributeName(i) == "component") {
                                    componentName = xmlPullParser.getAttributeValue(i)
                                } else if (xmlPullParser.getAttributeName(i) == "drawable") {
                                    drawableName = xmlPullParser.getAttributeValue(i)
                                }
                            }
                            if (drawableName == null) {
                                eventType = xmlPullParser.next()
                                continue
                            }
                            val drawableId = packResources.getIdentifier(drawableName, "drawable", iconPackPackageName)
                            if (drawableId != 0) {
                                val drawableInfo = SimpleDrawableInfo(drawableName, drawableId)
                                if (componentName != null) {
                                    iconPackToDrawablesMap.putIfAbsent(componentName, HashSet())
                                    iconPackToDrawablesMap.getValue(componentName).add(drawableInfo)
                                }
                            }
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (ignored: Exception) {
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun findAppFilterXml(): XmlPullParser? {
        val packResources = iconPackResources ?: return null

        var appFilterId = packResources.getIdentifier("appfilter", "xml", iconPackPackageName)
        if (appFilterId > 0) return packResources.getXml(appFilterId)

        appFilterId = packResources.getIdentifier("appfilter", "raw", iconPackPackageName)
        if (appFilterId > 0) {
            val input = packResources.openRawResource(appFilterId)
            return XmlPullParserFactory.newInstance().newPullParser().apply {
                setInput(input, "UTF-8")
            }
        }
        return null
    }
}