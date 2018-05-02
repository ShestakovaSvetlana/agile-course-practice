package ru.unn.agile.Vectors3d.Model;

public class Vector3d {
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public Vector3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double length() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public Vector3d normalize() {
        if (isNotZero()) {
            double len = length();
            return new Vector3d(x / len, y / len, z / len);
        }
        return new Vector3d(0, 0, 0);
    }

    public double dotProduct(final Vector3d first) {
        return (x * first.x + y * first.y + z * first.z);
    }

    public Vector3d crossProduct(final Vector3d first) {
        double newX = y * first.z - z * first.y;
        double newY = -(x * first.z - z * first.x);
        double newZ = x * first.y - y * first.x;
        return new Vector3d(newX, newY, newZ);
    }


    public boolean equalCompletely(final Vector3d first) {
        return (getX() == first.getX()) && (getY() == first.getY()) && (getZ() == first.getZ());
    }

    public boolean equalNormalized(final Vector3d first) {
        return normalize().equalCompletely(first.normalize());
    }

    private boolean isNotZero() {
        return (getX() != 0) || (getY() != 0) || (getZ() != 0);
    }

    private double x;
    private double y;
    private double z;
}

