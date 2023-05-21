package com.d3st.e_coding.utils

/**
 * Class for recognize text with help Ml-kit
 */
class EcodingMlKitAnalyzer {

    /*  private fun recognizeBitmapText(imageBitmap: ImageBitmap) {
  val image = InputImage.fromBitmap(imageBitmap.asAndroidBitmap(), 0)
  recognizeText(image)
}

private fun recognizeTextByUri(pictureUri: Uri) {
  val image = InputImage.fromFilePath(this, pictureUri)
  recognizeText(image)
}

private fun recognizeText(image: InputImage) {
  val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
  recognizer.process(image)
      .addOnSuccessListener { result ->
          val resultWordList = mutableListOf<String>()
          val resultText = result.text
          for (block in result.textBlocks) {
              val blockText = block.text
              val blockCornerPoints = block.cornerPoints
              val blockFrame = block.boundingBox
              for (line in block.lines) {
                  val lineText = line.text
                  val lineCornerPoints = line.cornerPoints
                  val lineFrame = line.boundingBox
                  for (element in line.elements) {
                      val elementText = element.text
                      resultWordList.add(elementText)
                      val elementCornerPoints = element.cornerPoints
                      val elementFrame = element.boundingBox
                  }
              }
          }
          viewModel.addRecognizedText(resultText, resultWordList)
      }
      .addOnFailureListener { e ->
          Log.d(TAG, "fail recognize: $e")
          e.message?.let { viewModel.showError(it) }
      }
}*/
}