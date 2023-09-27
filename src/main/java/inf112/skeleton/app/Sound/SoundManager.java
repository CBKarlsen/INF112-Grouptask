package inf112.skeleton.app.Sound;

public class SoundManager {
    public aSound mainMenuMusic;
    public aSound arrowSound;
    public aSound lightningMultiShotSound;
    public aSound arenaSound;
    public aSound safeZone;
    public aSound buttonClick;
    public aSound house;
    public aSound hit;
    public aSound killedAllEnemies;
    public aSound start;
    public aSound kill;
    public aSound monsterdied;

    public SoundManager() {
        this.mainMenuMusic = new aSound("assets/soundfiles/Main.mp3", true);
        this.arrowSound = new aSound("assets/soundfiles/bow-release.mp3", false);
        this.lightningMultiShotSound = new aSound("assets/soundfiles/electric-shock.mp3", false);
        this.arenaSound = new aSound("assets/soundfiles/arena-2.mp3", true);
        this.safeZone = new aSound("assets/soundfiles/grasshopper.mp3", true);
        this.buttonClick = new aSound("assets/soundfiles/KnappeLyd.mp3", false);
        this.house = new aSound("assets/soundfiles/HouseMusic.mp3", true);
        this.hit = new aSound("assets/soundfiles/takehit.mp3", false);
        this.killedAllEnemies = new aSound("assets/soundfiles/dead.mp3", false);
        this.start = new aSound("assets/soundfiles/start.mp3", false);
        this.kill = new aSound("assets/soundfiles/killed.mp3", false);
        this.monsterdied = new aSound("assets/soundfiles/monsterdied.mp3", false);

    }

}
