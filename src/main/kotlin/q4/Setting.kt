package q4

import algo.PolyArea
import map.PlotMap
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import utils.Vector2
import utils.degree
import utils.nm
import utils.step
import java.io.FileInputStream

const val MAP_PATH = "../附件.xlsx"
const val DELTA_IN_NM = 0.02
const val X_START_NM = 0.0
const val Y_START_NM = 0.0
const val X_LENGTH_IN_NM = 4.0
const val Y_LENGTH_IN_NM = 5.0

val deeps = run {
    val file = FileInputStream(MAP_PATH)
    val workbook = XSSFWorkbook(file)
    val sheet = workbook.getSheetAt(0)
    val dp = mutableListOf<List<Double>>()
    (Y_START_NM..Y_LENGTH_IN_NM step DELTA_IN_NM).forEachIndexed { yIdx, _ ->
        val row = sheet.getRow(yIdx + 2)
        (X_START_NM..X_LENGTH_IN_NM step DELTA_IN_NM)
            .mapIndexed { xIdx, _ -> row.getCell(xIdx + 2).numericCellValue }
            .let { dp += it.toList() }
    }
    workbook.close()
    file.close()
    dp
}

val map = PlotMap(
    start = Vector2(X_START_NM.nm, Y_START_NM.nm),
    xDelta = DELTA_IN_NM.nm,
    yDelta = DELTA_IN_NM.nm,
    deeps = deeps,
)
val detectAngle = 120.degree
//val searchArea = RectArea(
//    startX = X_START_NM.nm,
//    endX = (X_LENGTH_IN_NM + X_START_NM).nm,
//    startY = Y_START_NM.nm,
//    endY = (Y_LENGTH_IN_NM + Y_START_NM).nm,
//    map = map,
//)

val searchArea = PolyArea(
    points = listOf(
        Vector2(X_START_NM.nm, Y_START_NM.nm),
        Vector2((X_LENGTH_IN_NM + X_START_NM).nm, (Y_LENGTH_IN_NM + Y_START_NM).nm),
        Vector2(X_START_NM.nm, (Y_LENGTH_IN_NM + Y_START_NM).nm),
    ),
    map = map,
)