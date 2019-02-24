package net.prosavage.bedfilevisualizer.bedfileclient

import java.io.File

class BedFileClient
/**
 * This runs the bed file client.
 *
 * @param files - files for the client to process
 */
{


        constructor(files: MutableList<File>) {
            this.files = files
            useMultiThreading = files.size > 2
        runtime = Runtime.getRuntime()
        }
    private var files: MutableList<File>? = null
    private var useMultiThreading: Boolean = false
    private var runtime: Runtime

    /**
     * Runs the intersect algorithm, there are no arguments.
     * This is the first part.
     */
    fun runIntersect(outputFile: File): File {
        if (useMultiThreading) {
            // TODO: Call python.
        }

        var processBuilder = ProcessBuilder()
        processBuilder.command("bedtools","intersect", "-a", this.files!![0].path, "-b" , files!![1].path)
        processBuilder.directory(this.files!![0].parentFile)
        processBuilder.redirectOutput(outputFile)
        processBuilder.start()
//        runtime.exec("bedtools intersect -a ${this.files!![0].absolutePath} -b ${files!![1].absolutePath}", null, File(files!![0].parent))
        System.out.println("done")
        return outputFile
    }

    /**
     * Runs the window algorithm.
     * This is the 2nd part.
     *
     * @param bp - the interval in base pairs
     */
    fun runWindow(bp: Int, outputFile: File): File {
        if (useMultiThreading) {
            //TODO: Call python.
        }
        var processBuilder = ProcessBuilder()
        processBuilder.command("bedtools","window", "-a", this.files!![0].path, "-b" , files!![1].path, "-w", bp.toString(), "output.bed")
        processBuilder.directory(this.files!![0].parentFile)
        processBuilder.redirectOutput(outputFile)
        processBuilder.start()
        return outputFile
    }
//N, K


}

