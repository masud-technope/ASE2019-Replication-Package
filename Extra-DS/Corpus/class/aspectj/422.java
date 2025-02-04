package moody;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

enum Mood implements  {

    HAPPY() {
    }
    , SAD() {
    }
    , JOLLY() {
    }
    , GRUMPY() {
    }
    ;
}

class AnnotationMoodImplementor {
}

@Aspect
class AnnotationMoodIndicator {

    public interface Moody {

        Mood getMood();

        void setMood(Mood mood);
    }

    public static class MoodyImpl implements Moody {

        private Mood mood = Mood.HAPPY;

        public Mood getMood() {
            return mood;
        }

        public void setMood(Mood mood) {
            this.mood = mood;
        }
    }

    @DeclareParents(value = "moody.AnnotationMoodImplementor", defaultImpl = MoodyImpl.class)
    private Moody implementedInterface;
}

public class MainClass {

    public static void main(String[] args) {
        AnnotationMoodImplementor ami0 = new AnnotationMoodImplementor();
        AnnotationMoodImplementor ami1 = new AnnotationMoodImplementor();
        System.err.println("ami0's mood is " + ((AnnotationMoodIndicator.Moody) ami0).getMood());
        ((AnnotationMoodIndicator.Moody) ami1).setMood(Mood.JOLLY);
        System.err.println("ami1's mood is now " + ((AnnotationMoodIndicator.Moody) ami1).getMood());
        System.err.println("ami0's mood is still " + ((AnnotationMoodIndicator.Moody) ami0).getMood());
    }
}
