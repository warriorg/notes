class Debounce {
 /***
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    /***
     * 延迟线程
     */
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /***
     * 延迟执行的map
     */
    private final ConcurrentHashMap<Object, Future<?>> delayedMap = new ConcurrentHashMap<>();


	/***
     * 更新进度
     *
     * @param code
     * @param progress
     */
    public void updateProgress(TaskCode code, int step, int progress) {
        if (logger.isDebugEnabled()) {
            logger.debug("触发更新任务进度 {}, {}, {}", code, step, progress);
        }
        final Future<?> prev = delayedMap.put(code.getCombineCode(), executorService.schedule(() -> {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("执行更新任务进度 {}, {}, {}", code, step, progress);
                }
                taskRepository.updateProgress(code, step, progress);
            } finally {
                delayedMap.remove(code.getCombineCode());
            }
        }, 1, TimeUnit.SECONDS));
        // 如果任务还没运行,则取消任务
        if (prev != null) {
            prev.cancel(true);
        }
    }
}