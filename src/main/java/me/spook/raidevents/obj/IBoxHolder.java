package me.spook.raidevents.obj;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a box holder, an entity that can have a 'box' around it.
 * <p>
 * very informative yes, you get it :D
 * </p>
 */
public interface IBoxHolder {

    /**
     * Checks if this box holder is colliding with another box holder.
     *
     * @param other the other box holder
     * @return true if the two box holders are colliding, false otherwise
     */

    default boolean checkCollision(IBoxHolder other) {
        final Location center = getCenter();
        final Location otherCenter = other.getCenter();

        return center.getWorld() == otherCenter.getWorld() &&
                Math.abs(center.getX() - otherCenter.getX()) < 1.0 &&
                Math.abs(center.getZ() - otherCenter.getZ()) < 1.0;
    }

    /**
     * Gets the center location of this box holder.
     *
     * @return the center location
     */

    Location getCenter();

    /**
     * Teleports a player to the center of this box holder.
     * <p>
     * NOTE:
     * You should replace this with a safety teleport, as normal bukkit teleport normally ends up like u falling
     * through the floor and shit, very unstable
     *
     * @param player the player to teleport
     */

    default void teleport(Player player) {
        final Location center = getCenter();
        if (center == null) return;

        player.teleport(center);
    }

    /**
     * Adds walls to this box holder.
     *
     * @param amount   The amount of walls
     * @param material The material of the walls
     * @param water    If the walls should be watered ;)
     */

    default void addWalls(int amount, Material material, boolean water) {
        Location center = getCenter();
        if (center == null) return;
        World world = center.getWorld();
        if (world == null) return;

        int currentWidth = getCurrentWidth();
        int currentHeight = getCurrentHeight();

        int halfWidth = (currentWidth - 1) / 2;
        int minX = center.getBlockX() - halfWidth;
        int maxX = center.getBlockX() + halfWidth;
        int minZ = center.getBlockZ() - halfWidth;
        int maxZ = center.getBlockZ() + halfWidth;

        int minY = center.getBlockY();
        int maxY = minY + currentHeight - 1;

        for (int i = 0; i < amount; i++) {
            // North wall (decrease Z)
            _generateWall(world, minX, maxX, minZ - 2 - i * 2, minZ - 2 - i * 2, minY, maxY, material, water);
            // South wall (increase Z)
            _generateWall(world, minX, maxX, maxZ + 2 + i * 2, maxZ + 2 + i * 2, minY, maxY, material, water);
            // West wall (decrease X)
            _generateWall(world, minX - 2 - i * 2, minX - 2 - i * 2, minZ, maxZ, minY, maxY, material, water);
            // East wall (increase X)
            _generateWall(world, maxX + 2 + i * 2, maxX + 2 + i * 2, minZ, maxZ, minY, maxY, material, water);
        }
    }

    /**
     * Generates a wall.
     *
     * @param world    The world to generate the wall in
     * @param x1       x1
     * @param x2       x2
     * @param z1       z1
     * @param z2       z2
     * @param y1       y1
     * @param y2       y2
     * @param material The material of the wall
     * @param water    If the wall should be watered ;)
     */

    @ApiStatus.Internal
    default void _generateWall(World world, int x1, int x2, int z1, int z2, int y1, int y2, Material material, boolean water) {

        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                for (int y = y1; y <= y2; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(material);
                }
            }
        }

        for (int x = Math.min(x1, x2) + 1; x <= Math.max(x1, x2) - 1; x++) {
            for (int z = Math.min(z1, z2) + 1; z <= Math.max(z1, z2) - 1; z++) {
                for (int y = y1; y <= y2; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.AIR);
                    if (water) {
                        block.setType(Material.WATER);
                    }
                }
            }
        }

        if (water) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                    Block block = world.getBlockAt(x, y2 + 1, z);
                    block.setType(Material.WATER);
                }
            }
        }

    }

    /**
     * Checks if a location is inside the box. (very useful for checking if a player is inside the box)
     *
     * @param location The location to check
     * @return true if the location is inside the box, false otherwise
     */

    default boolean isInsideBox(Location location) {
        Location center = getCenter();

        if (center == null) return false;
        World world = center.getWorld();
        if (world == null) return false;

        int currentWidth = getCurrentWidth();
        int currentHeight = getCurrentHeight();

        int minX = center.getBlockX() - (currentWidth / 2);
        int maxX = center.getBlockX() + ((currentWidth - 1) / 2);
        int minZ = center.getBlockZ() - (currentWidth / 2);
        int maxZ = center.getBlockZ() + ((currentWidth - 1) / 2);

        int minY = center.getBlockY();
        int maxY = minY + currentHeight - 1;

        return location.getWorld() == world &&
                location.getBlockX() >= minX && location.getBlockX() <= maxX &&
                location.getBlockY() >= minY && location.getBlockY() <= maxY &&
                location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ;
    }

    /**
     * Creates a box around this box holder.
     *
     * @param width    The width of the box
     * @param height   The height of the box
     * @param material The material of the box
     */

    default void createBox(int width, int height, Material material) {
        Location center = getCenter();

        if (center == null) return;
        World world = center.getWorld();
        if (world == null) return;

        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        int minX = centerX - (width / 2);
        int maxX = centerX + ((width - 1) / 2);
        int minZ = centerZ - (width / 2);
        int maxZ = centerZ + ((width - 1) / 2);

        int maxY = centerY + height - 1;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = centerY; y <= maxY; y++) {
                    if (x == minX || x == maxX || z == minZ || z == maxZ || y == centerY || y == maxY) {
                        world.getBlockAt(x, y, z).setType(material);
                    }
                }
            }
        }

        setCurrentWidth(width);
        setCurrentHeight(height);
    }

    // -------------------------------------------- //
    // GETTERS AND SETTERS
    // -------------------------------------------- //

    int getCurrentWidth();

    int getCurrentHeight();

    void setCurrentWidth(int width);

    void setCurrentHeight(int height);
}