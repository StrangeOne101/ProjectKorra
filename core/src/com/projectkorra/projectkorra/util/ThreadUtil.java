package com.projectkorra.projectkorra.util;

import com.projectkorra.projectkorra.ProjectKorra;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for ensuring that a task is run on the correct thread.
 * Ensures compatibility between Folia and non-Folia servers. */
public class ThreadUtil {

    /**
     * Runs a task on the same thread as an entity. On Spigot, this is the main
     * thread. On Folia, this is the thread that the entity is on.<br><br>
     *
     * @param entity The entity to run the task on.
     * @param runnable The task to run.
     */
    public static void ensureEntity(Entity entity, Runnable runnable) {
        if (ProjectKorra.isFolia()) {
            if (Bukkit.isOwnedByCurrentRegion(entity) || Bukkit.isStopping()) {
                runnable.run();
                return;
            }
            entity.getScheduler().execute(ProjectKorra.plugin, runnable, null, 1L);
        } else {
            if (Bukkit.isPrimaryThread()) {
                runnable.run();
                return;
            }
            Bukkit.getScheduler().runTask(ProjectKorra.plugin, runnable);
        }
    }

    /**
     * Runs a task on the same thread as an entity after a delay. On Spigot, this is the main
     * thread. On Folia, this is the thread that the entity is on.
     * @param entity The entity to run the task on.
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object ensureEntityLater(Entity entity, Runnable runnable, long delay) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            return entity.getScheduler().execute(ProjectKorra.plugin, runnable, null, delay);
        } else {
            return Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, runnable, delay);
        }
    }

    /**
     * Runs a task on the same thread as an entity after a delay and repeats it until cancelled.
     * On Spigot, this is the main thread. On Folia, this is the thread that the entity is on.
     * @param entity The entity to run the task on.
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @param repeat The delay in ticks between each repeat of the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object ensureEntityTimer(Entity entity, Runnable runnable, long delay, long repeat) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            return entity.getScheduler().runAtFixedRate(ProjectKorra.plugin, (task) -> runnable.run(), null, delay, repeat);
        } else {
            return Bukkit.getScheduler().runTaskTimer(ProjectKorra.plugin, runnable, delay, repeat);
        }
    }

    /**
     * Runs a task on the same thread as a location. On Spigot, this is the main
     * thread. On Folia, this is the thread that the location is in.
     * @param location The location to run the task on.
     * @param runnable The task to run.
     */
    public static void ensureLocation(Location location, Runnable runnable) {
        if (ProjectKorra.isFolia()) {
            if (Bukkit.isOwnedByCurrentRegion(location) || Bukkit.isStopping()) {
                runnable.run();
                return;
            }
            RegionScheduler scheduler = Bukkit.getRegionScheduler();
            scheduler.execute(ProjectKorra.plugin, location, runnable);
        } else {
            if (Bukkit.isPrimaryThread()) {
                runnable.run();
                return;
            }
            Bukkit.getScheduler().runTask(ProjectKorra.plugin, runnable);
        }
    }

    /**
     * Runs a task on the same thread as a location after a delay. On Spigot, this is the main
     * thread. On Folia, this is the thread that the location is in.
     * @param location The location to run the task on.
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object ensureLocationLater(@NotNull Location location, Runnable runnable, long delay) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            RegionScheduler scheduler = Bukkit.getRegionScheduler();
            return scheduler.runDelayed(ProjectKorra.plugin, location, (task) -> runnable.run(), delay);
        } else {
            return Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, runnable, delay);
        }
    }

    /**
     * Runs a task on the same thread as a location after a delay and repeats it until cancelled.
     * On Spigot, this is the main thread. On Folia, this is the thread that the location is in.
     * @param location The location to run the task on.
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @param repeat The delay in ticks between each repeat of the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object ensureLocationTimer(Location location, Runnable runnable, long delay, long repeat) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            RegionScheduler scheduler = Bukkit.getRegionScheduler();
            return scheduler.runAtFixedRate(ProjectKorra.plugin, location, (task) -> runnable.run(), delay, repeat);
        } else {
            return Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, runnable, delay);
        }
    }

    /**
     * Runs a task asynchronously.
     * @param runnable The task to run.
     */
    public static void runAsync(Runnable runnable) {
        if (ProjectKorra.isFolia()) {
            if (Bukkit.isStopping()) {
                runnable.run();
                return;
            }
            Bukkit.getAsyncScheduler().runNow(ProjectKorra.plugin, (task) -> runnable.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(ProjectKorra.plugin, runnable);
        }
    }

    /**
     * Runs a task asynchronously after a delay.
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object runAsyncLater(Runnable runnable, long delay) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            return Bukkit.getAsyncScheduler().runDelayed(ProjectKorra.plugin, (task) -> runnable.run(), delay * 50, TimeUnit.MILLISECONDS);
        } else {
            return Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, runnable, delay);
        }
    }

    /**
     * Runs a task asynchronously after a delay and repeats it until cancelled.
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @param repeat The delay in ticks between each repeat of the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object runAsyncTimer(Runnable runnable, long delay, long repeat) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            return Bukkit.getAsyncScheduler().runAtFixedRate(ProjectKorra.plugin, (task) -> runnable.run(), delay * 50, repeat * 50, TimeUnit.MILLISECONDS);
        } else {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(ProjectKorra.plugin, runnable, delay, repeat);
        }
    }

    /**
     * Runs a task synchronously. On Spigot, this is on the main thread. On Folia,
     * this is on the global region thread.<br><br>
     *
     * <b>Warning:</b> This should only be used for tasks that affect the world itself! Such as
     * modifying gamerules, the weather, world time, etc. It should not be used for tasks
     * that modify entities or the world, as those should be run using {@link #ensureEntity(Entity, Runnable)}
     * or {@link #ensureLocation(Location, Runnable)}.
     *
     * @param runnable The task to run.
     */
    public static void runGlobal(Runnable runnable) {
        if (ProjectKorra.isFolia()) {
            if (Bukkit.isStopping()) {
                runnable.run();
                return;
            }
            Bukkit.getGlobalRegionScheduler().run(ProjectKorra.plugin, (task) -> runnable.run());
        } else {
            Bukkit.getScheduler().runTask(ProjectKorra.plugin, runnable);
        }
    }

    /**
     * Runs a task synchronously after a delay. On Spigot, this is on the main thread.
     * On Folia, this is on the global region thread.<br><br>
     *
     * <b>Warning:</b> This should only be used for tasks that affect the world itself! Such as
     * modifying gamerules, the weather, world time, etc. It should not be used for tasks
     * that modify entities or the world, as those should be run using {@link #ensureEntityLater(Entity, Runnable, long)}
     * or {@link #ensureLocationLater(Location, Runnable, long)}.
     *
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object runGlobalLater(Runnable runnable, long delay) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            return Bukkit.getGlobalRegionScheduler().runDelayed(ProjectKorra.plugin, (task) -> runnable.run(), delay);
        } else {
            return  Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, runnable, delay);
        }
    }

    /**
     * Runs a task synchronously after a delay and repeats it until cancelled.
     * On Spigot, this is on the main thread. On Folia, this is on the global region thread.<br><br>
     *
     * <b>Warning:</b> This should only be used for tasks that affect the world itself! Such as
     * modifying gamerules, the weather, world time, etc. It should not be used for tasks
     * that modify entities or the world, as those should be run using
     * {@link #ensureEntityTimer(Entity, Runnable, long, long)}
     * or {@link #ensureLocationTimer(Location, Runnable, long, long)}
     *
     * @param runnable The task to run.
     * @param delay The delay in ticks before running the task.
     * @param repeat The delay in ticks between each repeat of the task.
     * @return The task object that can be used to cancel the task. Is a
     * {@link io.papermc.paper.threadedregions.scheduler.ScheduledTask} on Folia and a
     * {@link org.bukkit.scheduler.BukkitTask} on Spigot.
     */
    public static @NotNull Object runGlobalTimer(Runnable runnable, long delay, long repeat) {
        delay = Math.max(1, delay);
        if (ProjectKorra.isFolia()) {
            return Bukkit.getGlobalRegionScheduler().runAtFixedRate(ProjectKorra.plugin, (task) -> runnable.run(), delay, repeat);
        } else {
            return Bukkit.getScheduler().runTaskTimer(ProjectKorra.plugin, runnable, delay, repeat);
        }
    }

    /**
     * Cancels a task that was created with either timers or delayed tasks.
     * @param task The task to cancel. This is the object returned from
     * {@link #ensureLocationTimer(Location, Runnable, long, long)} or
     *             {@link #ensureEntityTimer(Entity, Runnable, long, long)}.
     * @return True if the task was cancelled successfully, false otherwise.
     */
    public static boolean cancelTask(@NotNull Object task) {
        if (ProjectKorra.isFolia()) {
            if (task instanceof io.papermc.paper.threadedregions.scheduler.ScheduledTask) {
                ((io.papermc.paper.threadedregions.scheduler.ScheduledTask) task).cancel();
                return true;
            }
        } else {
            if (task instanceof org.bukkit.scheduler.BukkitTask) {
                ((org.bukkit.scheduler.BukkitTask) task).cancel();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a task is cancelled.
     * @return True if the task is cancelled, false otherwise.
     */
    public static boolean isTaskCancelled(@NotNull Object task) {
        if (ProjectKorra.isFolia()) {
            if (task instanceof io.papermc.paper.threadedregions.scheduler.ScheduledTask) {
                return ((io.papermc.paper.threadedregions.scheduler.ScheduledTask) task).isCancelled();
            }
        } else {
            if (task instanceof org.bukkit.scheduler.BukkitTask) {
                return ((org.bukkit.scheduler.BukkitTask) task).isCancelled();
            }
        }
        return false;
    }
}
